package com.example.windimessenger.domain.usecase.profile

import com.example.windimessenger.domain.entity.network.ApiResponse
import com.example.windimessenger.domain.entity.network.Result
import com.example.windimessenger.domain.entity.network.Result.*
import com.example.windimessenger.domain.entity.profile.UserRequest
import com.example.windimessenger.domain.repository.ProfileRepository
import java.io.IOException
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke(userRequest: UserRequest): Result<Boolean> {
        return try {
            when (val result = profileRepository.updateUserInfo(userRequest)) {
                is ApiResponse.Error -> Error(message = result.detail.message)
                is ApiResponse.Success -> Success(data = true)
                is ApiResponse.ValidationError -> Error(message = result.detail[0].msg)
            }
        } catch (e: IOException) {
            Error(message = "Network error: ${e.message}")
        } catch (e: Exception) {
            Error(message = "Unexpected error: ${e.message}")
        }
    }
}