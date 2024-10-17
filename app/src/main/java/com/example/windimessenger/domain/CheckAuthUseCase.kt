package com.example.windimessenger.domain

import com.example.windimessenger.domain.entity.ApiResponse
import com.example.windimessenger.domain.entity.CheckAuthResponse
import com.example.windimessenger.domain.entity.Result
import java.io.IOException
import javax.inject.Inject

class CheckAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(phone: String, code: String): Result<ApiResponse<CheckAuthResponse>> {
        return try {
            when (val result = authRepository.checkAuthCode(phone, code)) {
                is ApiResponse.Error ->  Result.Error(message = result.detail.message)
                is ApiResponse.Success -> Result.Success(data = ApiResponse.Success(result.data))
                is ApiResponse.ValidationError ->  Result.Error(message = result.detail[0].msg)
            }
        } catch (e: IOException) {
            Result.Error(-1, "Network error: ${e.message}")
        } catch (e: Exception) {
            Result.Error(-1, "Unexpected error: ${e.message}")
        }
    }
}