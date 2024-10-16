package com.example.windimessenger.data.model

import com.google.gson.annotations.SerializedName

class AuthCodeResponseDto(
    @SerializedName("is_success") val isSuccess: Boolean
)