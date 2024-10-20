package com.example.windimessenger.di

import com.example.windimessenger.data.network.AuthService
import com.example.windimessenger.data.network.RefreshTokenService
import com.example.windimessenger.data.network.UserService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
object ApiModule {

    private const val BASE_URL = "https://plannerok.ru/"

    @Singleton
    @Provides
    fun provideAccessRetrofit(
        @AuthenticatedClient okHttpClient: OkHttpClient
    ): UserService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun provideRefreshRetrofit(
        @TokenRefreshClient okHttpClient: OkHttpClient
    ): RefreshTokenService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun provideUnauthenticatedRetrofit(
        @PublicClient okHttpClient: OkHttpClient
    ): AuthService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create()
    }
}