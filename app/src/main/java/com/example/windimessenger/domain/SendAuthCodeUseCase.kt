package com.example.windimessenger.domain

import javax.inject.Inject

class SendAuthCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(phone: String): Result<Boolean> {
        return try {
            Result.Success(authRepository.sendAuthCode(phone))
        } catch (e: Exception) {
            Result.Error(e.message.toString())
        }
    }
}