package com.example.windimessenger.data

import android.util.Log
import com.example.windimessenger.data.authentication.TokenManager
import com.example.windimessenger.data.mapper.toEntity
import com.example.windimessenger.data.model.request.CheckAuthCodeRequestDto
import com.example.windimessenger.data.model.request.SendAuthCodeRequestDto
import com.example.windimessenger.data.model.request.UserRegisterRequestDto
import com.example.windimessenger.data.model.response.AuthResponseDto
import com.example.windimessenger.data.model.response.CheckAuthResponseDto
import com.example.windimessenger.data.model.response.SendAuthCodeResponseDto
import com.example.windimessenger.data.network.AuthService
import com.example.windimessenger.domain.AuthRepository
import com.example.windimessenger.domain.entity.ApiResponse
import com.example.windimessenger.domain.entity.AuthState
import com.example.windimessenger.domain.entity.CheckAuthResponse
import com.example.windimessenger.domain.entity.ErrorDetail
import com.example.windimessenger.domain.entity.UserRegisterResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.json.Json
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val tokenManager: TokenManager
) : AuthRepository {

    private val checkAuthStateEvents = MutableSharedFlow<Unit>(replay = 1)
    private val authStateFlow = flow {
        checkAuthStateEvents.emit(Unit)
        checkAuthStateEvents.collect {
            val currentToken = tokenManager.getAccessJwt()
            val loggedIn = !currentToken.isNullOrBlank()
            val authState = if (loggedIn) AuthState.Authorized else AuthState.NotAuthorized
            emit(authState)
        }
    }.stateIn(
        scope = CoroutineScope(Dispatchers.Default),
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    override suspend fun sendAuthCode(phone: String): ApiResponse<Boolean> {
        return handleApiCall { authService.sendAuthCode(SendAuthCodeRequestDto(phone)) }
    }

    override suspend fun checkAuthCode(phone: String, code: String): ApiResponse<CheckAuthResponse> {
        return handleApiCall { authService.checkAuthCode(CheckAuthCodeRequestDto(phone, code)) }
    }

    override suspend fun registerUser(phone: String, name: String, username: String): ApiResponse<UserRegisterResponse> {
        return handleApiCall { authService.registerUser(UserRegisterRequestDto(phone, name, username)) }
    }

    override fun getAuthStateFlow() = authStateFlow

    override suspend fun checkAuthState() {
        checkAuthStateEvents.emit(Unit)
    }

    override suspend fun logout() {
        tokenManager.clearAllTokens()
        checkAuthStateEvents.emit(Unit)
    }

    private suspend inline fun <T, reified R> handleApiCall(apiCall: () -> Response<T>): ApiResponse<R> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    when (it) {
                        is SendAuthCodeResponseDto -> ApiResponse.Success(it.isSuccess as R)
                        is CheckAuthResponseDto -> {
                            val result = it.toEntity()
                            saveTokens(result.accessToken, result.refreshToken)
                            ApiResponse.Success(result as R)
                        }
                        is AuthResponseDto -> {
                            val result = it.toEntity()
                            saveTokens(result.accessToken, result.refreshToken)
                            ApiResponse.Success(result as R)
                        }
                        else -> ApiResponse.Error(ErrorDetail("Unexpected response body"))
                    }
                } ?: ApiResponse.Error(ErrorDetail("Empty response body"))
            } else {
                when (response.code()) {
                    422 -> response.errorBody()?.string()?.let {
                        val result = Json.decodeFromString<ApiResponse.ValidationError>(it)
                        ApiResponse.ValidationError(result.detail)
                    } ?: ApiResponse.Error(ErrorDetail("Error parsing validation error"))

                    404 -> response.errorBody()?.string()?.let {
                        val result = Json.decodeFromString<ApiResponse.Error>(it)
                        Log.i("MyTag", result.detail.message)
                        ApiResponse.Error(ErrorDetail(result.detail.message))
                    } ?: ApiResponse.Error(ErrorDetail("Error parsing not found error"))

                    else -> ApiResponse.Error(ErrorDetail("${response.code()} ${response.message()}"))
                }
            }
        } catch (e: Exception) {
            ApiResponse.Error(ErrorDetail(e.message ?: "Unknown error occurred"))
        }
    }

    private suspend fun saveTokens(accessToken: String, refreshToken: String) {
        tokenManager.saveAccessJwt(accessToken)
        tokenManager.saveRefreshJwt(refreshToken)
    }
}