package com.habibi.core.domain.authentication.usecase

import com.habibi.core.data.Resource

interface IAuthenticationUseCase {

    suspend fun postLogin(email: String, password: String): Resource<Unit>

    suspend fun postRegister(name: String, email: String, password: String): Resource<Unit>

}