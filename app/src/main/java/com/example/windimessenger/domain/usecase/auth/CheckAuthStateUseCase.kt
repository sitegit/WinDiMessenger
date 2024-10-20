package com.example.windimessenger.domain.usecase.auth

import com.example.windimessenger.domain.repository.AuthRepository
import javax.inject.Inject

class CheckAuthStateUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke() {
        repository.checkAuthState()
    }
}