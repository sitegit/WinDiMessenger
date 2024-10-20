package com.example.windimessenger.data.model.auth.response

import com.google.gson.annotations.SerializedName

class AuthResponseDto(
    @SerializedName("refresh_token") val refreshToken: String?,
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("user_id") val userId: Int?
)