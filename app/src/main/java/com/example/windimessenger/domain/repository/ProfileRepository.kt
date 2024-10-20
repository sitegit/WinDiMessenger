package com.example.windimessenger.domain.repository

import com.example.windimessenger.domain.entity.network.ApiResponse
import com.example.windimessenger.domain.entity.profile.UserRequest
import com.example.windimessenger.domain.entity.profile.UserInfo
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getUserFlow(): Flow<ApiResponse<UserInfo>>

    suspend fun updateUserInfo(userRequest: UserRequest): ApiResponse<Boolean>
}