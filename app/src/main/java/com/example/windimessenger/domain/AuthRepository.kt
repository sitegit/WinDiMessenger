package com.example.windimessenger.domain

import com.example.windimessenger.domain.entity.ApiResponse
import com.example.windimessenger.domain.entity.CheckAuthResponse
import com.example.windimessenger.domain.entity.UserRegisterResponse

interface AuthRepository {

    suspend fun sendAuthCode(phone: String): ApiResponse<Boolean>

    suspend fun checkAuthCode(phone: String, code: String): ApiResponse<CheckAuthResponse>

    suspend fun registerUser(phone: String, name: String, username: String): ApiResponse<UserRegisterResponse>
}