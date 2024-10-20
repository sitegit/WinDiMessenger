package com.example.windimessenger.domain.entity.profile

data class UserRequest(
    val avatarUrl: String,
    val avatarUri: String,
    val fileName: String,
    val name: String,
    val birthday: String,
    val city: String,
    val phone: String,
    val status: String,
    val username: String
)
