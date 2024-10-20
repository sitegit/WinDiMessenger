package com.example.windimessenger.presentation.screen.profile

import com.example.windimessenger.domain.entity.profile.UserInfo

sealed class ProfileState {
    data object Idle : ProfileState()
    data object Loading : ProfileState()
    data class Success(val data: UserInfo) : ProfileState()
    data class Error(val message: String) : ProfileState()
}