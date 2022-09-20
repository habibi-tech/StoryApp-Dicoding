package com.habibi.core.data

sealed class Resource<T>(val data: T? = null, val message: String = "", val messageResource: Int = 0) {
    class Loading<T> : Resource<T>()
    class Success<T>(data: T, message: String) : Resource<T>(data = data, message = message)
    class Failed<T>(message: String) : Resource<T>(message = message)
    class Error<T>(messageResource: Int) : Resource<T>(messageResource = messageResource)
}