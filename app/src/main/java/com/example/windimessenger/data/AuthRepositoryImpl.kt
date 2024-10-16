package com.example.windimessenger.data

import com.example.windimessenger.data.model.AuthResponseDto
import com.example.windimessenger.data.model.CheckAuthCodeRequestDto
import com.example.windimessenger.data.model.SendAuthCodeRequestDto
import com.example.windimessenger.data.model.SendAuthCodeResponseDto
import com.example.windimessenger.data.network.ApiService
import com.example.windimessenger.domain.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {

    override suspend fun sendAuthCode(phone: String): Boolean {
        val response: SendAuthCodeResponseDto = apiService.sendAuthCode(SendAuthCodeRequestDto(phone))
        return response.isSuccess
    }

    override suspend fun checkAuthCode(phone: String, code: String) {
        val response: AuthResponseDto = apiService.checkAuthCode(CheckAuthCodeRequestDto(phone, code))
        //return response
    }
}