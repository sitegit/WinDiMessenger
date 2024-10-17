package com.example.windimessenger.domain

import com.example.windimessenger.domain.entity.CheckAuthResponse
import com.example.windimessenger.domain.entity.Result
import com.example.windimessenger.domain.entity.SendAuthCodeResult
import com.example.windimessenger.domain.entity.UserRegisterResponse

interface AuthRepository {

    suspend fun sendAuthCode(phone: String): SendAuthCodeResult

    suspend fun checkAuthCode(phone: String, code: String): Result<CheckAuthResponse>

    suspend fun registerUser(phone: String, name: String, username: String): Result<UserRegisterResponse>
}