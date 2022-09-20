package com.habibi.core.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.first

@Singleton
class UserSessionDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val isLoggedInKey = booleanPreferencesKey("is_logged_in")
    private val userNameKey = stringPreferencesKey("user_name")
    private val tokenKey = stringPreferencesKey("token")

    suspend fun setLoggedIn(isLoggedIn: Boolean) {
        dataStore.edit { preference ->
            preference[isLoggedInKey] = isLoggedIn
        }
    }

    suspend fun isLoggedIn(): Boolean = dataStore.data.first()[isLoggedInKey] ?: false

    suspend fun setUserName(name: String) {
        dataStore.edit { preference ->
            preference[userNameKey] = name
        }
    }

    suspend fun getUserName(): String = dataStore.data.first()[userNameKey] ?: ""

    suspend fun setToken(token: String) {
        dataStore.edit { preference ->
            preference[tokenKey] = token
        }
    }

    suspend fun getToken(): String = dataStore.data.first()[tokenKey] ?: ""

}