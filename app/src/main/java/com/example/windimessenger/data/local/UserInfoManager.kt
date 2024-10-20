package com.example.windimessenger.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.windimessenger.domain.entity.profile.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserInfoManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val userName = stringPreferencesKey("user_name")
    private val userUsername = stringPreferencesKey("user_username")
    private val userCity = stringPreferencesKey("user_city")
    private val userAvatarUrl = stringPreferencesKey("user_avatar_url")
    private val userAvatarUri = stringPreferencesKey("user_avatar_uri")
    private val userBirthday = stringPreferencesKey("user_birthday")
    private val userPhone = stringPreferencesKey("user_phone")
    private val userStatus = stringPreferencesKey("user_status")

    val userFlow: Flow<UserInfo> = dataStore.data
        .map { preferences ->
            Log.i("MyTag", "Get avatarUrl: ${preferences[userAvatarUrl]}")
            UserInfo(
                avatarUrl = preferences[userAvatarUrl] ?: "",
                avatarUri = preferences[userAvatarUri] ?: "",
                birthday = preferences[userBirthday] ?: "",
                city = preferences[userCity] ?: "",
                phone = preferences[userPhone] ?: "",
                status = preferences[userStatus] ?: "",
                username = preferences[userUsername] ?: "",
                name = preferences[userName] ?: ""
            )
        }

    suspend fun saveUser(partialUser: UserInfo) {
        dataStore.edit { preferences ->
            Log.i("MyTag", "Saving avatarUrl: ${partialUser.avatarUrl}")
            partialUser.avatarUrl.takeIf { it.isNotBlank() }?.let { preferences[userAvatarUrl] = it }
            partialUser.birthday.takeIf { it.isNotBlank() }?.let { preferences[userBirthday] = it }
            partialUser.city.takeIf { it.isNotBlank() }?.let { preferences[userCity] = it }
            partialUser.phone.takeIf { it.isNotBlank() }?.let { preferences[userPhone] = it }
            partialUser.status.takeIf { it.isNotBlank() }?.let { preferences[userStatus] = it }
            partialUser.name.takeIf { it.isNotBlank() }?.let { preferences[userName] = it }
            partialUser.username.takeIf { it.isNotBlank() }?.let { preferences[userUsername] = it }
            partialUser.avatarUri.takeIf { it.isNotBlank() }?.let { preferences[userAvatarUri] = it }
        }
    }

    suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.remove(userName)
            preferences.remove(userUsername)
            preferences.remove(userCity)
            preferences.remove(userAvatarUrl)
            preferences.remove(userAvatarUri)
            preferences.remove(userBirthday)
            preferences.remove(userPhone)
            preferences.remove(userStatus)
        }
    }
}