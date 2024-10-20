package com.example.windimessenger.domain.repository

import com.example.windimessenger.domain.entity.network.ApiResponse
import com.example.windimessenger.domain.entity.network.AuthState
import com.example.windimessenger.domain.entity.network.CheckAuthResponse
import com.example.windimessenger.domain.entity.network.UserRegisterResponse
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    suspend fun sendAuthCode(phone: String): ApiResponse<Boolean>

    suspend fun checkAuthCode(phone: String, code: String): ApiResponse<CheckAuthResponse>

    suspend fun registerUser(phone: String, name: String, username: String): ApiResponse<UserRegisterResponse>

    fun getAuthStateFlow(): StateFlow<AuthState>

    suspend fun checkAuthState()

    suspend fun logout()
}