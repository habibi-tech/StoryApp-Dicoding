package com.habibi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@SerializedName("error")
	val error: Boolean = true,

	@SerializedName("message")
	val message: String = ""

)
