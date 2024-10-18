package com.example.windimessenger.data.local

import com.example.windimessenger.domain.entity.chat.Chat
import com.example.windimessenger.domain.entity.chat.Message
import com.github.javafaker.Faker
import javax.inject.Inject

class ChatsGenerator @Inject constructor(
    private val faker: Faker
) {

    fun generateFakeChats(): MutableList<Chat> {
        return MutableList(20) {
            Chat(
                id = it,
                name = faker.funnyName().name(),
                messages = generateFakeMessages()
            )
        }
    }

    private fun generateFakeMessages(): List<Message> {
        return List(15) {
            Message(
                id = it,
                content = faker.lorem().sentence(),
                isSendByUser = it % 2 == 0
            )
        }
    }
}

fun main() {
    val chat = ChatsGenerator(Faker())
    println(chat.generateFakeChats())
}