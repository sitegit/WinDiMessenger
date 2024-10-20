package com.example.windimessenger.data.repository

import android.util.Log
import com.example.windimessenger.data.local.UserInfoManager
import com.example.windimessenger.data.mapper.toDto
import com.example.windimessenger.data.mapper.toEntity
import com.example.windimessenger.data.mapper.toUserInfo
import com.example.windimessenger.data.network.UserService
import com.example.windimessenger.domain.entity.network.ApiResponse
import com.example.windimessenger.domain.entity.network.ErrorDetail
import com.example.windimessenger.domain.entity.profile.UserInfo
import com.example.windimessenger.domain.entity.profile.UserRequest
import com.example.windimessenger.domain.repository.ProfileRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import retrofit2.Response
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val userInfoManager: UserInfoManager
) : ProfileRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getUserFlow(): Flow<ApiResponse<UserInfo>> = userInfoManager.userFlow
        .flatMapLatest { localData ->
            flow {
                if (isUserInfoEmpty(localData)) {
                    val response = userService.getUserInfo()
                    if (response.isSuccessful) {
                        response.body()?.let { userInfoDto ->
                            val avatarUrl = "https://plannerok.ru/${userInfoDto.profileDataDto.avatarsDto.avatar}"
                            val userInfo = userInfoDto.toEntity().copy(avatarUri = avatarUrl)
                            userInfoManager.saveUser(userInfo)
                            emit(ApiResponse.Success(userInfo))
                        } ?: emit(ApiResponse.Success(localData))
                    } else {
                        emit(getError(response))
                    }
                } else {
                    emit(ApiResponse.Success(localData))
                }
            }
        }

    override suspend fun updateUserInfo(userRequest: UserRequest) : ApiResponse<Boolean> {
        val response = userService.updateUserInfo(
            userRequest.toDto().copy(birthday = userRequest.birthday.ifBlank { "1990-01-01" })
        )

        return if (response.isSuccessful) {
            Log.i("MyTag", "sdfdf")
            userInfoManager.saveUser(userRequest.toUserInfo())
            ApiResponse.Success(true)
        } else {
            getError(response)
        }
    }

    private fun isUserInfoEmpty(userInfo: UserInfo): Boolean {
        return userInfo.name.isEmpty() && userInfo.username.isEmpty() && userInfo.phone.isEmpty()
    }

    private fun <T>getError(response: Response<T>): ApiResponse<Nothing> {
        return when (response.code()) {
            422 -> response.errorBody()?.string()?.let {
                val result = Json.decodeFromString<ApiResponse.ValidationError>(it)
                ApiResponse.ValidationError(result.detail)
            } ?: ApiResponse.Error(ErrorDetail("Error parsing validation error"))

            else -> ApiResponse.Error(ErrorDetail("${response.code()} ${response.message()}"))
        }
    }
}