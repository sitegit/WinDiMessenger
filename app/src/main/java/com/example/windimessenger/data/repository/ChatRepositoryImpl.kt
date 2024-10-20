package com.example.windimessenger.data.repository

import com.example.windimessenger.data.local.ChatsGenerator
import com.example.windimessenger.data.local.UserInfoManager
import com.example.windimessenger.domain.entity.chat.Chat
import com.example.windimessenger.domain.entity.profile.UserInfo
import com.example.windimessenger.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatGenerator: ChatsGenerator,
    private val userInfoManager: UserInfoManager
) : ChatRepository {

    private val _chats: MutableList<Chat> = chatGenerator.generateFakeChats()
    private val chats: List<Chat>
        get() = _chats.toList()

    override fun getUserFlow(): Flow<UserInfo> = userInfoManager.userFlow

    override fun getChatList(): List<Chat> {
        return chats
    }

    override fun getChat(id: Int): Chat {
        return chats[id]
    }
}