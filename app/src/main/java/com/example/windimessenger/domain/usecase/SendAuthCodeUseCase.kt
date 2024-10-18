package com.example.windimessenger.domain.usecase

import com.example.windimessenger.domain.AuthRepository
import com.example.windimessenger.domain.entity.ApiResponse
import com.example.windimessenger.domain.entity.Result
import java.io.IOException
import javax.inject.Inject

class SendAuthCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(phone: String): Result<ApiResponse<Boolean>> {
        return try {
            when (val result = authRepository.sendAuthCode(phone)) {
                is ApiResponse.Error ->  Result.Error(message = result.detail.message)
                is ApiResponse.Success -> Result.Success(data = ApiResponse.Success(result.data))
                is ApiResponse.ValidationError -> Result.Error(message = result.detail[0].msg)
            }
        } catch (e: IOException) {
            Result.Error(message = "Network error: ${e.message}")
        } catch (e: Exception) {
            Result.Error(message = "Unexpected error: ${e.message}")
        }
    }
}