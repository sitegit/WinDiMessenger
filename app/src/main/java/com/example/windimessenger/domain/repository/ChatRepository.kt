package com.example.windimessenger.domain.repository

import com.example.windimessenger.domain.entity.chat.Chat
import com.example.windimessenger.domain.entity.profile.UserInfo
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    fun getUserFlow(): Flow<UserInfo>

    fun getChatList(): List<Chat>

    fun getChat(id: Int): Chat
}