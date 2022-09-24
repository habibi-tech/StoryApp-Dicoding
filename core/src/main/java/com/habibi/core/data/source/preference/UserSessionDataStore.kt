package com.habibi.core.data.source.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.habibi.core.data.source.preference.constant.UserSessionConstant
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.first

@Singleton
class UserSessionDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val isLoggedInKey = booleanPreferencesKey(UserSessionConstant.KEY_IS_LOGGED_IN)
    private val userNameKey = stringPreferencesKey(UserSessionConstant.KEY_USERNAME)
    private val tokenKey = stringPreferencesKey(UserSessionConstant.KEY_TOKEN)

    suspend fun setLoggedIn() {
        dataStore.edit { preference ->
            preference[isLoggedInKey] = true
        }
    }

    suspend fun setLogout() {
        dataStore.edit { preference ->
            preference[isLoggedInKey] = false
        }
    }

    suspend fun isLoggedIn(): Boolean = dataStore.data.first()[isLoggedInKey] ?: false

    suspend fun setUserName(name: String) {
        dataStore.edit { preference ->
            preference[userNameKey] = name
        }
    }

    suspend fun getUserName(): String = dataStore.data.first()[userNameKey] ?: ""

    suspend fun removeUserName() {
        dataStore.edit { preference ->
            preference.remove(userNameKey)
        }
    }

    suspend fun setToken(token: String) {
        dataStore.edit { preference ->
            preference[tokenKey] = token
        }
    }

    suspend fun getToken(): String = dataStore.data.first()[tokenKey] ?: ""

    suspend fun removeToken() {
        dataStore.edit { preference ->
            preference.remove(tokenKey)
        }
    }

}