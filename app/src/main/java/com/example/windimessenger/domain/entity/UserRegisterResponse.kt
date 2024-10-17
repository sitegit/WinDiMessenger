package com.example.windimessenger.domain.entity

class UserRegisterResponse(
    val refreshToken: String,
    val accessToken: String,
    val userId: Int
)