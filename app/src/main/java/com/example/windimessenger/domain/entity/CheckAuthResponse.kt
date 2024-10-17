package com.example.windimessenger.domain.entity

data class CheckAuthResponse(
    val refreshToken: String?,
    val accessToken: String?,
    val userId: Int?,
    val isUserExists: Boolean
)
