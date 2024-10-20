package com.example.windimessenger.data.model.profile

import com.google.gson.annotations.SerializedName

data class AvatarsDto(
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("bigAvatar") val bigAvatar: String?,
    @SerializedName("miniAvatar") val miniAvatar: String?
)