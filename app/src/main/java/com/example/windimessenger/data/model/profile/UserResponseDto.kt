package com.example.windimessenger.data.model.profile

import com.google.gson.annotations.SerializedName

data class UserResponseDto(
    @SerializedName("profile_data") val profileDataDto: ProfileDataDto
)