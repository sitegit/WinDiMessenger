package com.example.windimessenger.domain.entity.chat

data class Chat(
    val id: Int,
    val name: String,
    val messages: List<Message>
)
