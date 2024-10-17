package com.example.windimessenger.domain

import com.example.windimessenger.domain.entity.Result
import com.example.windimessenger.domain.entity.UserRegisterResponse
import java.io.IOException
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(phone: String, name: String, username: String): Result<UserRegisterResponse> {
        return try {
            when (val result = authRepository.registerUser(phone, name, username)) {
                is Result.Success -> result
                is Result.Error -> {
                    when (result.errorCode) {
                        422 -> Result.Error(422, "Validation error: ${result.message}")
                        400 -> Result.Error(400, "Bad Request: ${result.message}")
                        500 -> Result.Error(500, "Server error: ${result.message}")
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