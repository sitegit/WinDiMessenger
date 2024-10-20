package com.example.windimessenger.data.model.profile

import com.google.gson.annotations.SerializedName

data class ProfileDataDto(
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("avatars") val avatarsDto: AvatarsDto,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("city") val city: String?,
    //@SerializedName("id") val id: Int,
    @SerializedName("phone") val phone: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("username") val username: String?,
)