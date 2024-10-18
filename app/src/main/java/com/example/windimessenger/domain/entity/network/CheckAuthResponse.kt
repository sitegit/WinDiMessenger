package com.example.windimessenger.domain.entity.network

data class CheckAuthResponse(
    val refreshToken: String,
    val accessToken: String,
    val userId: Int,
    val isUserExists: Boolean
)
