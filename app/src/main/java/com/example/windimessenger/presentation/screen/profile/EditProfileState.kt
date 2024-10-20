package com.example.windimessenger.presentation.screen.profile

sealed class EditProfileState {
    data object Idle : EditProfileState()
    data object Loading : EditProfileState()
    data object Success : EditProfileState()
    data class Error(val message: String) : EditProfileState()
}