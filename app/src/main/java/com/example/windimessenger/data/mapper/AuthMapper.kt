package com.example.windimessenger.data.mapper

import com.example.windimessenger.data.model.CheckAuthResponseDto
import com.example.windimessenger.data.model.UserRegisterResponseDto
import com.example.windimessenger.domain.entity.CheckAuthResponse
import com.example.windimessenger.domain.entity.UserRegisterResponse

fun CheckAuthResponseDto.toEntity() = CheckAuthResponse(
    refreshToken = refreshToken,
    accessToken = accessToken,
    userId = userId,
    isUserExists = isUserExists
)

fun UserRegisterResponseDto.toEntity() = UserRegisterResponse(
    refreshToken = refreshToken,
    accessToken = accessToken,
    userId = userId
)