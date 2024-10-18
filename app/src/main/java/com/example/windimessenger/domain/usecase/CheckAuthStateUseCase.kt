package com.example.windimessenger.domain.usecase

import com.example.windimessenger.domain.AuthRepository
import javax.inject.Inject

class CheckAuthStateUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke() {
        repository.checkAuthState()
    }
}