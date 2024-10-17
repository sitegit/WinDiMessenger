package com.example.windimessenger.di

import androidx.lifecycle.ViewModel
import com.example.windimessenger.presentation.MainViewModel
import com.example.windimessenger.presentation.screens.login.LoginViewModel
import com.example.windimessenger.presentation.screens.signup.SignUpViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    @Binds
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    @Binds
    fun signUpViewModel(viewModel: SignUpViewModel): ViewModel
}