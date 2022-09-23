package com.habibi.core.domain.authentication

import com.habibi.core.data.Resource
import com.habibi.core.domain.authentication.usecase.IAuthenticationUseCase
import com.habibi.core.domain.repository.IUserSessionRepository
import javax.inject.Inject

class AuthenticationInteractor @Inject constructor(
    private val userSessionRepository: IUserSessionRepository
): IAuthenticationUseCase {

    override suspend fun postLogin(email: String, password: String): Resource<Unit> =
        userSessionRepository.postLogin(email, password)

    override suspend fun postRegister(
        name: String,
        email: String,
        password: String
    ): Resource<Unit>  =
        userSessionRepository.postRegister(name, email, password)
}