package com.example.windimessenger.presentation.screen.chat

import androidx.lifecycle.ViewModel
import com.example.windimessenger.domain.entity.chat.Chat
import com.example.windimessenger.domain.usecase.GetChatListUseCase
import com.example.windimessenger.domain.usecase.GetChatUseCase
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val getChatListUseCase: GetChatListUseCase,
    private val getChatUseCase: GetChatUseCase
) : ViewModel() {

    val chats = getChatListUseCase()

    fun getChat(chatId: Int): Chat {
        return getChatUseCase(chatId)
    }
}