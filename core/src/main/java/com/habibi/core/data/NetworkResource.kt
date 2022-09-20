package com.habibi.core.data

import com.habibi.core.data.source.remote.network.ApiResponse

abstract class NetworkResource<ResultType, RequestType> {

    suspend fun result(): Resource<ResultType> {
        return when (val apiResponse = createCall()) {
            is ApiResponse.Success -> {
                val data = onSuccess(apiResponse.data)
                Resource.Success(data, apiResponse.message)
            }
            is ApiResponse.Failed -> {
                Resource.Failed(apiResponse.errorMessage)
            }
            is ApiResponse.Error -> {
                Resource.Error(apiResponse.errorResource)
            }
        }
    }

    protected abstract suspend fun createCall(): ApiResponse<RequestType>

    protected abstract suspend fun onSuccess(data: RequestType): ResultType

}