package com.softprodigy.technicaltest.data.remote

import javax.inject.Inject


class UsersRemoteDataSource @Inject constructor(private val usersApiService : UsersApiService):BaseDataSource(){
    suspend fun getUsers() =  getResult { usersApiService.getUsers() }

}
