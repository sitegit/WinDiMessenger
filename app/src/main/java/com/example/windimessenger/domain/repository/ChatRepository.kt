package com.example.windimessenger.domain.repository

import com.example.windimessenger.domain.entity.chat.Chat

interface ChatRepository {

    fun getChatList(): List<Chat>

    fun getChat(id: Int): Chat
}