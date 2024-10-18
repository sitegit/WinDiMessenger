package com.example.windimessenger.domain.entity.network

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorCode: Int = -1, val message: String) : Result<Nothing>()
}