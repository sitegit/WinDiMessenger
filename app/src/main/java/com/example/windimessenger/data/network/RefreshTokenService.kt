package com.example.windimessenger.data.network

import com.example.windimessenger.data.model.RefreshTokenRequest
import com.example.windimessenger.data.model.auth.response.AuthResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenService {

    @POST("/api/v1/users/refresh-token/")
    suspend fun refreshToken(
        @Body refreshToken: RefreshTokenRequest
    ): Response<AuthResponseDto>
}