package com.example.windimessenger.domain

import javax.inject.Inject

class CheckAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(phone: String, code: String) {
        try {
            authRepository.checkAuthCode(phone, code)
        } catch (e: Exception) {
            //Result.Error(e.message.toString())
        }
    }
}