package com.habibi.core.data.source.remote.network

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T, val message: String) : ApiResponse<T>()
    data class Failed(val errorMessage: String) : ApiResponse<Nothing>()
    data class Error(val errorResource: Int) : ApiResponse<Nothing>()
}