package com.habibi.core.domain.splashscreen.usecase

interface ISplashScreenUseCase {

    suspend fun isLoggedIn(): Boolean

}