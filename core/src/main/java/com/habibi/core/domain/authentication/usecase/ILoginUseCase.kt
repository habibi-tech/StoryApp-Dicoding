package com.habibi.core.domain.authentication.usecase

import com.habibi.core.data.Resource

interface ILoginUseCase {

    suspend fun postLogin(email: String, password: String): Resource<Unit>

}