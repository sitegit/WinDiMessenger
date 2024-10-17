package com.example.windimessenger.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.windimessenger.data.AuthRepositoryImpl
import com.example.windimessenger.data.authentication.TokenManager
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
    }
}