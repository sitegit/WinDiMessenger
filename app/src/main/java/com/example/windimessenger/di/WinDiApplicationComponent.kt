package com.example.windimessenger.di

import android.content.Context
import com.example.windimessenger.presentation.utils.ViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [DataModule::class, ViewModelModule::class]
)
interface WinDiApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): WinDiApplicationComponent
    }
}