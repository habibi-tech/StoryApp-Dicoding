package com.habibi.core.domain.repository

import com.habibi.core.data.Resource

interface IUserSessionRepository {

    suspend fun setUserLogout()

    suspend fun isLoggedIn(): Boolean

    suspend fun getUserName(): String

    suspend fun postRegister(name: String, email: String, password: String): Resource<Unit>

    suspend fun postLogin(email: String, password: String): Resource<Unit>

}