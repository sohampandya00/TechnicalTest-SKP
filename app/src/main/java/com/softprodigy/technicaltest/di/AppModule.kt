package com.softprodigy.technicaltest.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.softprodigy.technicaltest.data.remote.UsersApiService
import com.softprodigy.technicaltest.data.remote.UsersRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideUserApiService(retrofit: Retrofit): UsersApiService = retrofit.create(UsersApiService::class.java)

    @Singleton
    @Provides
    fun provideUsersRemoteDataSource(apiservice: UsersApiService) = UsersRemoteDataSource(apiservice)


}