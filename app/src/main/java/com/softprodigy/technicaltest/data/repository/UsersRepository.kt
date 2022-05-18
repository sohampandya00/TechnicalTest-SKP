package com.softprodigy.technicaltest.data.repository

import com.softprodigy.technicaltest.data.remote.UsersApiService
import javax.inject.Inject

class UsersRepository @Inject constructor (private val retrofitService: UsersApiService) {

    suspend fun getUsers() = retrofitService.getUsers()
}