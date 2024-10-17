package com.example.windimessenger.domain.entity

sealed class SendAuthCodeResult {
    data class Success(val isSuccess: Boolean): SendAuthCodeResult()

    data class ValidationError(
        val detail: List<ValidationDetail>
    ) : SendAuthCodeResult()

    data class Error(val message: String) : SendAuthCodeResult()
}
