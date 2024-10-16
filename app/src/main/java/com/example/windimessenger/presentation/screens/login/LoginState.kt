package com.example.windimessenger.presentation.screens.login

sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data class Success(val isValidAuthCode: Boolean, val phone: String) : LoginState()
    data class Error(val message: String) : LoginState()
}