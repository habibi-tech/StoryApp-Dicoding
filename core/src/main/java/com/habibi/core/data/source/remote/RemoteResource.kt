package com.habibi.core.data.source.remote

import com.habibi.core.R
import com.habibi.core.data.source.remote.network.ApiResponse
import com.habibi.core.utils.wrapEspressoIdlingResource
import retrofit2.Response

abstract class RemoteResource<ResultType, ResponseType> {

    suspend fun result(): ApiResponse<ResultType> = wrapEspressoIdlingResource {
        try {
            val response = createCall()

            when (response.code()) {
                in 200..299 -> {
                    val dataSuccess = response.body()
                    if (dataSuccess != null) {
                        when {
                            !isError(dataSuccess) -> {
                                return ApiResponse.Success(
                                    dataSuccess(dataSuccess),
                                    message(dataSuccess)
                                )
                            }
                            else -> {
                                return ApiResponse.Failed(message(dataSuccess))
                            }
                        }
                    } else {
                        return errorValue
                    }
                }
                in 400..499 -> {
                    val dataFailed = dataResponseFailed(response.errorBody()?.string() ?: "")
                    return ApiResponse.Failed(message(dataFailed))
                }
                else -> {
                    return errorValue
                }
            }
        } catch (e: Exception) {
            return errorValue
        }
    }

    protected abstract suspend fun dataResponseFailed(stringJson: String): ResponseType

    protected abstract suspend fun isError(dataResponse: ResponseType): Boolean

    protected abstract suspend fun message(dataResponse: ResponseType): String

    protected abstract suspend fun dataSuccess(dataResponse: ResponseType): ResultType

    protected abstract suspend fun createCall(): Response<ResponseType>

    companion object {
        private val errorValue = ApiResponse.Error(R.string.something_wrong)
    }
}