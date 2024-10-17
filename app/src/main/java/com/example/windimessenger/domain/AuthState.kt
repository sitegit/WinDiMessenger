package com.example.windimessenger.domain

sealed class AuthState {

    data object Authorized : AuthState()
    data object NotAuthorized : AuthState()
    data object Initial : AuthState()
}