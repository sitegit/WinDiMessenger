package com.example.windimessenger.data.model.request

import com.google.gson.annotations.SerializedName

data class CheckAuthCodeRequestDto(
    @SerializedName("phone") val phone: String,
    @SerializedName("code") val code: String
)