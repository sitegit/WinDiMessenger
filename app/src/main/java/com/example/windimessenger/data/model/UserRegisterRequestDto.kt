package com.example.windimessenger.data.model

import com.google.gson.annotations.SerializedName

data class UserRegisterRequestDto(
    @SerializedName("phone") val phone: String,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String
)