package com.example.windimessenger.domain.usecase

import com.example.windimessenger.domain.AuthRepository
import com.example.windimessenger.domain.entity.AuthState
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    operator fun invoke(): StateFlow<AuthState> {
        return repository.getAuthStateFlow()
    }
}