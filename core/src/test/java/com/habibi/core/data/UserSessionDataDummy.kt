package com.habibi.core.data

import com.habibi.core.data.source.remote.network.ApiResponse
import com.habibi.core.data.source.remote.response.LoginResult

object UserSessionDataDummy {

    const val name = "Habibi"
    const val email = "habibi@mail.com"
    const val password = "123456"
    private const val message = "message"
    private const val messageResource = 1

    val postRegisterSuccess = ApiResponse.Success(Unit, message)
    val postRegisterFailed = ApiResponse.Failed(message)
    val postRegisterError = ApiResponse.Error(messageResource)

    val postLoginSuccess = ApiResponse.Success(
        LoginResult(
            name = "habibi",
            userId = "123",
            token = "token"
        ),
        message
    )
    val postLoginFailed = ApiResponse.Failed(message)
    val postLoginError = ApiResponse.Error(messageResource)

}