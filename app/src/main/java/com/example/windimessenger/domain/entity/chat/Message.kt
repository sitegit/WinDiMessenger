package com.example.windimessenger.domain.entity.chat

data class Message(
    val id: Int,
    val content: String,
    val isSendByUser: Boolean
)
