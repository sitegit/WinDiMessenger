package com.example.windimessenger.domain.usecase

import com.example.windimessenger.domain.entity.chat.Chat
import com.example.windimessenger.domain.repository.ChatRepository
import javax.inject.Inject

class GetChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {

    operator fun invoke(chatId: Int): Chat {
        return chatRepository.getChat(chatId)
    }
}