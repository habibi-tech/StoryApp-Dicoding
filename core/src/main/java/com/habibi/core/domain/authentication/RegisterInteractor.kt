package com.habibi.core.domain.authentication

import com.habibi.core.data.Resource
import com.habibi.core.domain.authentication.usecase.IRegisterUseCase
import com.habibi.core.domain.repository.IUserSessionRepository
import javax.inject.Inject

class RegisterInteractor @Inject constructor(
    private val userSessionRepository: IUserSessionRepository
): IRegisterUseCase {

    override suspend fun postRegister(
        name: String,
        email: String,
        password: String
    ): Resource<Unit> =
        userSessionRepository.postRegister(name, email, password)

}