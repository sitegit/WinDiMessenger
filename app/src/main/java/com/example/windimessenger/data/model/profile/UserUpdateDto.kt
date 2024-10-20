package com.example.windimessenger.data.model.profile

import com.google.gson.annotations.SerializedName

data class UserUpdateDto(
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("birthday") val birthday: String,
    @SerializedName("city") val city: String,
    @SerializedName("status") val status: String,
    @SerializedName("avatar") val avatar: Avatar,
)