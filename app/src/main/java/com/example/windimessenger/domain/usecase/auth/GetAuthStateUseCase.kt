package com.example.windimessenger.domain.usecase.auth

import com.example.windimessenger.domain.repository.AuthRepository
import com.example.windimessenger.domain.entity.network.AuthState
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    operator fun invoke(): StateFlow<AuthState> {
        return repository.getAuthStateFlow()
    }
}