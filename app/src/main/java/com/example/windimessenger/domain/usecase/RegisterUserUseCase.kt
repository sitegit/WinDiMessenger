package com.example.windimessenger.domain.usecase

import com.example.windimessenger.domain.AuthRepository
import com.example.windimessenger.domain.entity.ApiResponse
import com.example.windimessenger.domain.entity.Result
import com.example.windimessenger.domain.entity.UserRegisterResponse
import java.io.IOException
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(phone: String, name: String, username: String): Result<ApiResponse<UserRegisterResponse>> {
        return try {
            when (val result = authRepository.registerUser(phone, name, username)) {
                is ApiResponse.Error ->  Result.Error(message = result.detail.message)
                is ApiResponse.Success -> Result.Success(data = ApiResponse.Success(result.data))
                is ApiResponse.ValidationError -> Result.Error(message = result.detail[0].msg)
            }
        } catch (e: IOException) {
            Result.Error(-1, "Network error: ${e.message}")
        } catch (e: Exception) {
            Result.Error(-1, "Unexpected error: ${e.message}")
        }
    }
}