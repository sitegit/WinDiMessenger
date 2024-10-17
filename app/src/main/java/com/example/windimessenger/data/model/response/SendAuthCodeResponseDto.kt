package com.example.windimessenger.data.model.response

import com.google.gson.annotations.SerializedName

class SendAuthCodeResponseDto(
    @SerializedName("is_success") val isSuccess: Boolean
)