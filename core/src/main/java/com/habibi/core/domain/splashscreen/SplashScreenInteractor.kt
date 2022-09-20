package com.habibi.core.domain.splashscreen

import com.habibi.core.domain.repository.IUserSessionRepository
import com.habibi.core.domain.splashscreen.usecase.ISplashScreenUseCase
import javax.inject.Inject

class SplashScreenInteractor @Inject constructor(
    private val userSessionRepository: IUserSessionRepository
): ISplashScreenUseCase {

    override suspend fun isLoggedIn(): Boolean =
        userSessionRepository.isLoggedIn()

}