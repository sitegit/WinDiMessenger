package com.example.windimessenger.data.model

import com.google.gson.annotations.SerializedName

data class AuthCodeRequestDto(
    @SerializedName("phone") val phone: String
)