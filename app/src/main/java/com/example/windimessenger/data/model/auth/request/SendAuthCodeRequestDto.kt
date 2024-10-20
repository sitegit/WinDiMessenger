package com.example.windimessenger.data.model.auth.request

import com.google.gson.annotations.SerializedName

data class SendAuthCodeRequestDto(
    @SerializedName("phone") val phone: String
)