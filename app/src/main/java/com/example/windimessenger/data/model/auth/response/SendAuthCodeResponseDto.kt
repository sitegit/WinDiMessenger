package com.example.windimessenger.data.model.auth.response

import com.google.gson.annotations.SerializedName

class SendAuthCodeResponseDto(
    @SerializedName("is_success") val isSuccess: Boolean
)