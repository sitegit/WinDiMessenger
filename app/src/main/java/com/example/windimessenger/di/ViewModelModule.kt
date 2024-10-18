package com.example.windimessenger.di

import androidx.lifecycle.ViewModel
import com.example.windimessenger.presentation.MainViewModel
import com.example.windimessenger.presentation.screen.chat.ChatViewModel
import com.example.windimessenger.presentation.screen.login.LoginViewModel
import com.example.windimessenger.presentation.screen.profile.ProfileViewModel
import com.example.windimessenger.presentation.screen.signup.SignUpViewModel
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
    fun bindSignUpViewModel(viewModel: SignUpViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    @Binds
    fun bindChatViewModel(viewModel: ChatViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    @Binds
    fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel
}