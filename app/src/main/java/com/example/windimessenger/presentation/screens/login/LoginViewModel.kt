package com.example.windimessenger.presentation.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.windimessenger.domain.CheckAuthUseCase
import com.example.windimessenger.domain.entity.Result
import com.example.windimessenger.domain.SendAuthCodeUseCase
import com.example.windimessenger.domain.entity.CheckAuthResponse
import com.example.windimessenger.domain.entity.SendAuthCodeResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val sendAuthCodeUseCase: SendAuthCodeUseCase,
    private val checkAuthUseCase: CheckAuthUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Idle)
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()



    fun sendNumber(phone: String) {
        viewModelScope.launch {
            _uiState.value = LoginState.Loading

            when (val result: Result<SendAuthCodeResult> = sendAuthCodeUseCase(phone)) {
                is Result.Success -> {
                    val data = result.data as SendAuthCodeResult.Success
                    Log.i("MyTag", data.isSuccess.toString())
                    _uiState.value = LoginState.Success(data.isSuccess, phone)
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

            when (val result: Result<CheckAuthResponse> = checkAuthUseCase(phone, code)) {
                is Result.Success -> {
                    _uiState.value = LoginState.Success(result.data.isUserExists, phone)
                }
                is Result.Error -> {
                    _uiState.value = LoginState.Error(result.message)
                }
            }
        }
    }

    fun authorizeUser() {

    }

    fun resetState() {
        _uiState.value = LoginState.Idle
    }
}