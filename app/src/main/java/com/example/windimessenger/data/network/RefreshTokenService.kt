package com.example.windimessenger.data.network

import com.example.windimessenger.data.model.response.AuthResponseDto
import retrofit2.Response
import retrofit2.http.POST

interface RefreshTokenService {

    @POST("/api/v1/users/refresh-token/")
    suspend fun refreshToken(): Response<AuthResponseDto>
}