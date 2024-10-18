package com.example.windimessenger.domain.entity

sealed class AuthState {

    data object Authorized : AuthState()
    data object NotAuthorized : AuthState()
    data object Initial : AuthState()
}