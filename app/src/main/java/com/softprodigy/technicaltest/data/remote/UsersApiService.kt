package com.softprodigy.technicaltest.data.remote

import com.softprodigy.technicaltest.data.entities.UsersModel
import retrofit2.Response
import retrofit2.http.GET

interface UsersApiService {

    @GET("users")
    suspend fun getUsers(
    ): Response<UsersModel>
}