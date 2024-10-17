package com.example.windimessenger.data

import com.example.windimessenger.data.mapper.toEntity
import com.example.windimessenger.data.model.CheckAuthCodeRequestDto
import com.example.windimessenger.data.model.CheckAuthResponseDto
import com.example.windimessenger.data.model.SendAuthCodeRequestDto
import com.example.windimessenger.data.model.UserRegisterRequestDto
import com.example.windimessenger.data.model.UserRegisterResponseDto
import com.example.windimessenger.data.network.ApiService
import com.example.windimessenger.domain.AuthRepository
import com.example.windimessenger.domain.entity.CheckAuthResponse
import com.example.windimessenger.domain.entity.Result
import com.example.windimessenger.domain.entity.SendAuthCodeResult
import com.example.windimessenger.domain.entity.SendAuthCodeResult.Error
import com.example.windimessenger.domain.entity.UserRegisterResponse
import kotlinx.serialization.json.Json
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {

    override suspend fun sendAuthCode(phone: String): SendAuthCodeResult {
        val response = apiService.sendAuthCode(SendAuthCodeRequestDto(phone))
        return handleSendAuthCodeResponse(response)
    }

    override suspend fun checkAuthCode(phone: String, code: String): Result<CheckAuthResponse> {
        val response: Response<CheckAuthResponseDto> = apiService.checkAuthCode(CheckAuthCodeRequestDto(phone, code))

        return if (response.isSuccessful) {
            response.body()?.let { body ->
                Result.Success(body.toEntity())
            } ?: Result.Error(response.code(), "Empty response body")
        } else {
            Result.Error(response.code(), response.message())
        }
    }

    override suspend fun registerUser(phone: String, name: String, username: String): Result<UserRegisterResponse> {
        val response: Response<UserRegisterResponseDto> =
            apiService.registerUser(UserRegisterRequestDto(phone, name, username))

        return if (response.isSuccessful) {
            response.body()?.let { body ->
                Result.Success(body.toEntity())
            } ?: Result.Error(response.code(), "Empty response body")
        } else {
            Result.Error(response.code(), response.message())
        }
    }

    private fun handleSendAuthCodeResponse(response: Response<SendAuthCodeResult>): SendAuthCodeResult {
        return if (response.isSuccessful) {
            response.body()?.let {
                when (it) {
                    is SendAuthCodeResult.Success -> it
                    else -> SendAuthCodeResult.Error("Unexpected response type")
                }
            } ?: SendAuthCodeResult.Error("Empty response body")
        } else {
            when (response.code()) {
                422 -> response.errorBody()?.string()?.let {
                    Json.decodeFromString<SendAuthCodeResult.ValidationError>(it)
                } ?: SendAuthCodeResult.Error("Empty error body")
                else -> SendAuthCodeResult.Error("Unexpected response code: ${response.code()}")
            }
        }
    }
}