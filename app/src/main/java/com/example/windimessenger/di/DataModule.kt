package com.example.windimessenger.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.windimessenger.data.repository.AuthRepositoryImpl
import com.example.windimessenger.data.authentication.TokenManager
import com.example.windimessenger.data.local.ChatsGenerator
import com.example.windimessenger.data.repository.ChatRepositoryImpl
import com.example.windimessenger.domain.repository.AuthRepository
import com.example.windimessenger.domain.repository.ChatRepository
import com.github.javafaker.Faker
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataModule {

    @Singleton
    @Binds
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    fun bindChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository

    companion object {

        @Singleton
        @Provides
        fun provideDataStore(context: Context): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                corruptionHandler = ReplaceFileCorruptionHandler(
                    produceNewData = { emptyPreferences() }
                ),
                produceFile = { context.preferencesDataStoreFile("settings") }
            )
        }

        @Singleton
        @Provides
        fun provideTokenManager(dataStore: DataStore<Preferences>): TokenManager {
            return TokenManager(dataStore)
        }

        @Singleton
        @Provides
        fun provideFaker() = Faker()

        @Singleton
        @Provides
        fun provideChatGenerator(faker: Faker) = ChatsGenerator(faker)
    }
}