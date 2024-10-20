package com.example.windimessenger.data.model.profile

import com.google.gson.annotations.SerializedName

data class Avatar(
    @SerializedName("filename") val filename: String,
    @SerializedName("base_64") val avatar: String,
)