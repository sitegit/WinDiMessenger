package com.example.windimessenger.data.network

import com.example.windimessenger.data.model.CheckAuthCodeRequestDto
import com.example.windimessenger.data.model.CheckAuthResponseDto
import com.example.windimessenger.data.model.SendAuthCodeRequestDto
import com.example.windimessenger.data.model.UserRegisterRequestDto
import com.example.windimessenger.data.model.UserRegisterResponseDto
import com.example.windimessenger.domain.entity.SendAuthCodeResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(
        @Body request: SendAuthCodeRequestDto
    ): Response<SendAuthCodeResult>

    @POST("/api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(
        @Body request: CheckAuthCodeRequestDto
    ): Response<CheckAuthResponseDto>

    @POST("/api/v1/users/register/")
    suspend fun registerUser(
        @Body request: UserRegisterRequestDto
    ): Response<UserRegisterResponseDto>
}
