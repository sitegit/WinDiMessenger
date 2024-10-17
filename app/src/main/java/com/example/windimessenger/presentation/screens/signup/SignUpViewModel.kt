package com.example.windimessenger.presentation.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.windimessenger.domain.RegisterUserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    fun registerUser(phone: String, name: String, username: String) {
        viewModelScope.launch {
            registerUserUseCase(phone, name, username)
        }
    }


}