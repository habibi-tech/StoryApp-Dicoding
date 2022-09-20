package com.habibi.core.data

import com.habibi.core.data.source.local.UserSessionDataStore
import com.habibi.core.data.source.remote.RemoteDataSource
import com.habibi.core.data.source.remote.network.ApiResponse
import com.habibi.core.data.source.remote.response.LoginResult
import com.habibi.core.domain.repository.IUserSessionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSessionRepository  @Inject constructor(
    private val userSessionDataStore: UserSessionDataStore,
    private val remoteDataSource: RemoteDataSource
): IUserSessionRepository {

    override suspend fun setUserLogout() {
        userSessionDataStore.setLoggedIn(false)
    }

    override suspend fun isLoggedIn(): Boolean =
        userSessionDataStore.isLoggedIn()

    override suspend fun getUserName(): String =
        userSessionDataStore.getUserName()

    override suspend fun postRegister(name: String, email: String, password: String): Resource<Unit> =
        object : NetworkResource<Unit, Unit>() {
            override suspend fun createCall(): ApiResponse<Unit> =
                remoteDataSource.postRegister(name, email, password)
            override suspend fun onSuccess(data: Unit) {}
        }.result()

    override suspend fun postLogin(email: String, password: String): Resource<Unit> =
        object : NetworkResource<Unit, LoginResult>() {
            override suspend fun createCall(): ApiResponse<LoginResult> =
                remoteDataSource.postLogin(email, password)
            override suspend fun onSuccess(data: LoginResult) {
                userSessionDataStore.apply {
                    setUserName(data.name)
                    setToken(data.token)
                    setLoggedIn(true)
                }
            }
        }.result()

}