package com.example.windimessenger.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component
interface WinDiApplicationComponent {

    //fun getViewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): WinDiApplicationComponent
    }
}