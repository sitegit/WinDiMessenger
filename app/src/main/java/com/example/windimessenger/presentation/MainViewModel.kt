package com.example.windimessenger.presentation

import androidx.lifecycle.ViewModel
import com.example.windimessenger.domain.usecase.GetAuthStateUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAuthStateUseCase: GetAuthStateUseCase
) : ViewModel() {

    val authState = getAuthStateUseCase()

}