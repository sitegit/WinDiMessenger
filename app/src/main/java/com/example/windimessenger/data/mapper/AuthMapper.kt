package com.example.windimessenger.data.mapper

import com.example.windimessenger.data.model.response.CheckAuthResponseDto
import com.example.windimessenger.data.model.response.AuthResponseDto
import com.example.windimessenger.domain.entity.CheckAuthResponse
import com.example.windimessenger.domain.entity.UserRegisterResponse

fun CheckAuthResponseDto.toEntity() = CheckAuthResponse(
    refreshToken = refreshToken,
    accessToken = accessToken,
    userId = userId,
    isUserExists = isUserExists
)

fun AuthResponseDto.toEntity() = UserRegisterResponse(
    refreshToken = refreshToken,
    accessToken = accessToken,
    userId = userId
)