package com.example.windimessenger.domain.entity.network

import kotlinx.serialization.Serializable

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T): ApiResponse<T>()
    @Serializable
    data class ValidationError(val detail: List<ValidationDetail>) : ApiResponse<Nothing>()

    @Serializable
    data class Error(val detail: ErrorDetail) : ApiResponse<Nothing>()

}