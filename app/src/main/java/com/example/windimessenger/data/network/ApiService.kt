package com.example.windimessenger.data.network


import com.example.windimessenger.data.model.AuthCodeRequestDto
import com.example.windimessenger.data.model.AuthCodeResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(
        @Body request: AuthCodeRequestDto
    ): AuthCodeResponseDto
}
