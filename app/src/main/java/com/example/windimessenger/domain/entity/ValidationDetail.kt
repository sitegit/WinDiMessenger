package com.example.windimessenger.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ValidationDetail(
    val loc: List<String>,
    val msg: String,
    val type: String
)
