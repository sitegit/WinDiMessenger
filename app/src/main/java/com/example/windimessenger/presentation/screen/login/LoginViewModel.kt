package com.example.windimessenger.presentation.screen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.windimessenger.domain.usecase.CheckAuthUseCase
import com.example.windimessenger.domain.usecase.SendAuthCodeUseCase
import com.example.windimessenger.domain.entity.ApiResponse
import com.example.windimessenger.domain.entity.CheckAuthResponse
import com.example.windimessenger.domain.entity.Result
import com.example.windimessenger.domain.usecase.CheckAuthStateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val sendAuthCodeUseCase: SendAuthCodeUseCase,
    private val checkAuthUseCase: CheckAuthUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Idle)
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()



    fun sendNumber(phone: String) {
        viewModelScope.launch {
            _uiState.value = LoginState.Loading

            when (val result: Result<ApiResponse<Boolean>> = sendAuthCodeUseCase(phone)) {
                is Result.Success -> {
                    val data = result.data as ApiResponse.Success<Boolean>
                    _uiState.value = LoginState.Success(data.data, phone)
                }
                is Result.Error -> {
                    _uiState.value = LoginState.Error(result.message)
                }
            }
        }
    }

    fun checkAuthUser(phone: String, code: String) {
        viewModelScope.launch {
            _uiState.value = LoginState.Loading

            when (val result: Result<ApiResponse<CheckAuthResponse>> = checkAuthUseCase(phone, code)) {
                is Result.Success -> {
                    val data = result.data as ApiResponse.Success<CheckAuthResponse>
                    Log.i( "MyTag", " -------------------${data.data.isUserExists}")
                    _uiState.value = LoginState.Success(data.data.isUserExists, phone)
                }
                is Result.Error -> {
                    Log.i( "MyTag", result.message)
                    _uiState.value = LoginState.Error(result.message)
                }
            }
        }
    }

    fun checkAuth() {
        viewModelScope.launch {
            checkAuthStateUseCase()
        }
    }

    fun resetState() {
        _uiState.value = LoginState.Idle
    }
}