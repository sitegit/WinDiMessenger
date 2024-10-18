package com.example.windimessenger.presentation.screen.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.windimessenger.domain.entity.ApiResponse
import com.example.windimessenger.domain.entity.Result
import com.example.windimessenger.domain.entity.UserRegisterResponse
import com.example.windimessenger.domain.usecase.CheckAuthStateUseCase
import com.example.windimessenger.domain.usecase.RegisterUserUseCase
import com.example.windimessenger.presentation.screen.login.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<SignUpState> = MutableStateFlow(SignUpState.Idle)
    val uiState: StateFlow<SignUpState> = _uiState.asStateFlow()

    fun registerUser(phone: String, name: String, username: String) {
        viewModelScope.launch {
            _uiState.value = SignUpState.Loading

            when (val result:  Result<ApiResponse<UserRegisterResponse>> = registerUserUseCase(phone, name, username)) {
                is Result.Success -> {
                    _uiState.value = SignUpState.Success
                }
                is Result.Error -> {
                    _uiState.value = SignUpState.Error(result.message)
                }
            }
        }
    }

    fun checkAuth() {
        viewModelScope.launch {
            checkAuthStateUseCase()
        }
    }
}