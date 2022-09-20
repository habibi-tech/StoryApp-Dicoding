package com.habibi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@SerializedName("loginResult")
	val loginResult: LoginResult = LoginResult(),

	@SerializedName("error")
	val error: Boolean = true,

	@SerializedName("message")
	val message: String = ""

)

data class LoginResult(

	@SerializedName("name")
	val name: String = "",

	@SerializedName("userId")
	val userId: String = "",

	@SerializedName("token")
	val token: String = ""

)
