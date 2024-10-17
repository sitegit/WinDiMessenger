package com.example.windimessenger.data.model

import com.google.gson.annotations.SerializedName

class UserRegisterResponseDto(
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("user_id") val userId: Int
)