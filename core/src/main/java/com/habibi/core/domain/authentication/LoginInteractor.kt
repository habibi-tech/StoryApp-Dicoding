package com.habibi.core.domain.authentication

import com.habibi.core.data.Resource
import com.habibi.core.domain.repository.IUserSessionRepository
import com.habibi.core.domain.authentication.usecase.ILoginUseCase
import javax.inject.Inject

class LoginInteractor @Inject constructor(
    private val userSessionRepository: IUserSessionRepository
): ILoginUseCase {

    override suspend fun postLogin(email: String, password: String): Resource<Unit> =
        userSessionRepository.postLogin(email, password)

}