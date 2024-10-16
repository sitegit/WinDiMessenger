package com.example.windimessenger.data.network

import com.example.windimessenger.data.model.AuthResponseDto
import com.example.windimessenger.data.model.CheckAuthCodeRequestDto
import com.example.windimessenger.data.model.SendAuthCodeRequestDto
import com.example.windimessenger.data.model.SendAuthCodeResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(
        @Body request: SendAuthCodeRequestDto
    ): SendAuthCodeResponseDto

    @POST("/api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(@Body request: CheckAuthCodeRequestDto): AuthResponseDto
}
