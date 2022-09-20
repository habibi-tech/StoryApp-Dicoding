package com.habibi.core.domain.authentication.usecase

import com.habibi.core.data.Resource

interface IRegisterUseCase {

    suspend fun postRegister(name: String, email: String, password: String): Resource<Unit>

}