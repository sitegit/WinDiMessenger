package com.example.windimessenger.domain

import com.example.windimessenger.domain.entity.Result
import com.example.windimessenger.domain.entity.SendAuthCodeResult
import java.io.IOException
import javax.inject.Inject

class SendAuthCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(phone: String): Result<SendAuthCodeResult> {
        return try {
            when (val result = authRepository.sendAuthCode(phone)) {
                is SendAuthCodeResult.Error -> Result.Error(message = result.message)
                is SendAuthCodeResult.Success -> Result.Success(data = SendAuthCodeResult.Success(result.isSuccess))
                is SendAuthCodeResult.ValidationError -> Result.Error(message = result.detail[0].msg)
            }
        } catch (e: IOException) {
            Result.Error(message = "Network error: ${e.message}")
        } catch (e: Exception) {
            Result.Error(message = "Unexpected error: ${e.message}")
        }
    }
}