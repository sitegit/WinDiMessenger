package com.example.windimessenger.data

import android.util.Log
import com.example.windimessenger.data.mapper.toEntity
import com.example.windimessenger.data.model.request.CheckAuthCodeRequestDto
import com.example.windimessenger.data.model.response.CheckAuthResponseDto
import com.example.windimessenger.data.model.request.SendAuthCodeRequestDto
import com.example.windimessenger.data.model.response.SendAuthCodeResponseDto
import com.example.windimessenger.data.model.request.UserRegisterRequestDto
import com.example.windimessenger.data.model.response.AuthResponseDto
import com.example.windimessenger.data.network.AuthService
import com.example.windimessenger.domain.AuthRepository
import com.example.windimessenger.domain.entity.ApiResponse
import com.example.windimessenger.domain.entity.CheckAuthResponse
import com.example.windimessenger.domain.entity.ErrorDetail
import com.example.windimessenger.domain.entity.UserRegisterResponse
import kotlinx.serialization.json.Json
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {

    override suspend fun sendAuthCode(phone: String): ApiResponse<Boolean> {
        return handleApiCall { authService.sendAuthCode(SendAuthCodeRequestDto(phone)) }
    }

    override suspend fun checkAuthCode(phone: String, code: String): ApiResponse<CheckAuthResponse> {
        return handleApiCall { authService.checkAuthCode(CheckAuthCodeRequestDto(phone, code)) }
    }

    override suspend fun registerUser(phone: String, name: String, username: String): ApiResponse<UserRegisterResponse> {
        return handleApiCall { authService.registerUser(UserRegisterRequestDto(phone, name, username)) }
    }

    //https://medium.com/@ratko.kostov21/jwt-authentication-in-android-using-retrofit-and-authenticator-b7b66e231295

    private suspend fun <T, R> handleApiCall(apiCall: suspend () -> Response<T>): ApiResponse<R> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    when (it) {
                        is SendAuthCodeResponseDto -> ApiResponse.Success(it.isSuccess as R)
                        is CheckAuthResponseDto -> ApiResponse.Success(it.toEntity() as R)
                        is AuthResponseDto -> ApiResponse.Success(it.toEntity() as R)
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
}