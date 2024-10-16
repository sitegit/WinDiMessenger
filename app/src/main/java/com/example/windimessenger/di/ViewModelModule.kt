package com.example.windimessenger.di

import androidx.lifecycle.ViewModel
import com.example.windimessenger.presentation.screens.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    @Binds
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel
}