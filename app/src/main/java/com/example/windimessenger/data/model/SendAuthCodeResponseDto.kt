package com.example.windimessenger.data.model

import com.google.gson.annotations.SerializedName

class SendAuthCodeResponseDto(
    @SerializedName("is_success") val isSuccess: Boolean
)