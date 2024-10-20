package com.example.windimessenger.data.model.profile

import com.google.gson.annotations.SerializedName

data class AvatarsDto(
    @SerializedName("avatar") val avatar: String?
)