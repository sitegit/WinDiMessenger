package com.example.windimessenger.domain.entity.network

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDetail(val message: String)