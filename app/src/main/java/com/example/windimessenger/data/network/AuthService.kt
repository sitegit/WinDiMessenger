package com.example.windimessenger.data.network

import com.example.windimessenger.data.model.auth.request.CheckAuthCodeRequestDto
import com.example.windimessenger.data.model.auth.response.CheckAuthResponseDto
import com.example.windimessenger.data.model.auth.request.SendAuthCodeRequestDto
import com.example.windimessenger.data.model.auth.response.SendAuthCodeResponseDto
import com.example.windimessenger.data.model.auth.request.UserRegisterRequestDto
import com.example.windimessenger.data.model.auth.response.AuthResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(
        @Body request: SendAuthCodeRequestDto
    ): Response<SendAuthCodeResponseDto>

    @POST("/api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(
        @Body request: CheckAuthCodeRequestDto
    ): Response<CheckAuthResponseDto>

    @POST("/api/v1/users/register/")
    suspend fun registerUser(
        @Body request: UserRegisterRequestDto
    ): Response<AuthResponseDto>
}
