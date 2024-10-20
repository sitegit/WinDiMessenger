package com.example.windimessenger.data.network

import com.example.windimessenger.data.model.profile.UserResponseDto
import com.example.windimessenger.data.model.profile.UserUpdateDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface UserService {

    @GET("/api/v1/users/me/")
    suspend fun getUserInfo(): Response<UserResponseDto>

    @PUT("/api/v1/users/me/")
    suspend fun updateUserInfo(
        @Body updateRequest: UserUpdateDto
    ): Response<UserUpdateDto>
}