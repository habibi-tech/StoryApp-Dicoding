package com.habibi.core.data.source.remote

import com.google.gson.Gson
import com.habibi.core.data.source.remote.network.ApiResponse
import com.habibi.core.data.source.remote.network.ApiService
import com.habibi.core.data.source.remote.response.ListStoryItem
import com.habibi.core.data.source.remote.response.ListStoryResponse
import com.habibi.core.data.source.remote.response.LoginResponse
import com.habibi.core.data.source.remote.response.LoginResult
import com.habibi.core.data.source.remote.response.NewStoryResponse
import com.habibi.core.data.source.remote.response.RegisterResponse
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.MultipartBody
import retrofit2.Response

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson
) {

    suspend fun postRegister(name: String, email: String, password: String): ApiResponse<Unit> =
        object : RemoteResource<Unit, RegisterResponse>() {
            override suspend fun dataResponseFailed(stringJson: String): RegisterResponse =
                gson.fromJson(stringJson, RegisterResponse::class.java)
            override suspend fun isError(dataResponse: RegisterResponse): Boolean =
                dataResponse.error
            override suspend fun message(dataResponse: RegisterResponse): String =
                dataResponse.message
            override suspend fun dataSuccess(dataResponse: RegisterResponse) =
                Unit
            override suspend fun createCall(): Response<RegisterResponse> =
                apiService.postRegister(name, email, password)
        }.result()

    suspend fun postLogin(email: String, password: String): ApiResponse<LoginResult> =
        object : RemoteResource<LoginResult, LoginResponse>() {
            override suspend fun dataResponseFailed(stringJson: String): LoginResponse =
                gson.fromJson(stringJson, LoginResponse::class.java)
            override suspend fun isError(dataResponse: LoginResponse): Boolean =
                dataResponse.error
            override suspend fun message(dataResponse: LoginResponse): String =
                dataResponse.message
            override suspend fun dataSuccess(dataResponse: LoginResponse): LoginResult =
                dataResponse.loginResult
            override suspend fun createCall(): Response<LoginResponse> =
                apiService.postLogin(email, password)
        }.result()

    suspend fun postNewStory(photo: MultipartBody.Part, description: String): ApiResponse<Unit> =
        object : RemoteResource<Unit, NewStoryResponse>() {
            override suspend fun dataResponseFailed(stringJson: String): NewStoryResponse =
                gson.fromJson(stringJson, NewStoryResponse::class.java)
            override suspend fun isError(dataResponse: NewStoryResponse): Boolean =
                dataResponse.error
            override suspend fun message(dataResponse: NewStoryResponse): String =
                dataResponse.message
            override suspend fun dataSuccess(dataResponse: NewStoryResponse) =
                Unit
            override suspend fun createCall(): Response<NewStoryResponse> =
                apiService.postNewStory(photo, description)
        }.result()

    suspend fun getListStories(token: String): ApiResponse<List<ListStoryItem>> =
        object : RemoteResource<List<ListStoryItem>, ListStoryResponse>() {
            override suspend fun dataResponseFailed(stringJson: String): ListStoryResponse =
                gson.fromJson(stringJson, ListStoryResponse::class.java)
            override suspend fun isError(dataResponse: ListStoryResponse): Boolean =
                dataResponse.error
            override suspend fun message(dataResponse: ListStoryResponse): String =
                dataResponse.message
            override suspend fun dataSuccess(dataResponse: ListStoryResponse): List<ListStoryItem> =
                dataResponse.listStory
            override suspend fun createCall(): Response<ListStoryResponse> =
                apiService.getListStories(
                    mapOf("Authorization" to "Bearer $token")
                )
        }.result()

}