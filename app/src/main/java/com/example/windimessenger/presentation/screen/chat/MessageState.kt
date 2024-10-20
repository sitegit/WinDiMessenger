package com.example.windimessenger.presentation.screen.chat

import com.example.windimessenger.domain.entity.profile.UserInfo

sealed class MessageState {
    data object Idle : MessageState()
    data object Loading : MessageState()
    data class Success(val data: UserInfo) : MessageState()
    data class Error(val message: String) : MessageState()
}