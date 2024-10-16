package com.example.windimessenger.domain

interface AuthRepository {

    suspend fun sendAuthCode(phone: String): Boolean

    suspend fun checkAuthCode(phone: String, code: String)
}