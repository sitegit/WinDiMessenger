package com.example.windimessenger.domain.usecase.profile

import android.util.Log
import com.example.windimessenger.domain.entity.network.ApiResponse
import com.example.windimessenger.domain.entity.network.Result
import com.example.windimessenger.domain.entity.network.Result.Error
import com.example.windimessenger.domain.entity.profile.UserInfo
import com.example.windimessenger.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    operator fun invoke(): Flow<Result<UserInfo>> {
        return profileRepository.getUserFlow()
            .map { apiResponse ->
                when (apiResponse) {
                    is ApiResponse.Error -> { Error(message = apiResponse.detail.message) }
                    is ApiResponse.Success -> Result.Success(apiResponse.data)
                    is ApiResponse.ValidationError -> { Error(message = apiResponse.detail[0].msg) }
                }
            }.catch { error ->
                Log.i("MyTag", error.message.toString())
                emit(Error(message = error.message ?: "Unknown error occurred"))
            }
    }
}