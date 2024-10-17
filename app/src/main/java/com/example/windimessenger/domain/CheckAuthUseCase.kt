package com.example.windimessenger.domain

import com.example.windimessenger.domain.entity.CheckAuthResponse
import com.example.windimessenger.domain.entity.Result
import java.io.IOException
import javax.inject.Inject

class CheckAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(phone: String, code: String): Result<CheckAuthResponse> {
        return try {
            when (val result = authRepository.checkAuthCode(phone, code)) {
                is Result.Success -> result
                is Result.Error -> {
                    when (result.errorCode) {
                        422 -> Result.Error(422, "Validation error: ${result.message}")
                        404 -> Result.Error(404, "Incorrect code entered: ${result.message}")
                        else -> Result.Error(result.errorCode, "Unknown error: ${result.message}")
                    }
                }
            }
        } catch (e: IOException) {
            Result.Error(-1, "Network error: ${e.message}")
        } catch (e: Exception) {
            Result.Error(-1, "Unexpected error: ${e.message}")
        }
    }
}