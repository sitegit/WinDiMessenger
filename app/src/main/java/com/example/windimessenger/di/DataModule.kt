package com.example.windimessenger.di

import com.example.windimessenger.data.AuthRepositoryImpl
import com.example.windimessenger.data.network.ApiFactory
import com.example.windimessenger.data.network.ApiService
import com.example.windimessenger.domain.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataModule {

    @Singleton
    @Binds
    fun bindAuthRepository(authRepositoryImp: AuthRepositoryImpl): AuthRepository

    companion object {

        @Singleton
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}