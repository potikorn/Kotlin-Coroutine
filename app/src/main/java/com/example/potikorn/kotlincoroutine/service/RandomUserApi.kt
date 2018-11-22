package com.example.potikorn.kotlincoroutine.service

import com.example.potikorn.kotlincoroutine.model.RandomUserModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {

    @GET(".")
    fun getRandomUser(): Deferred<Response<RandomUserModel>>

    @GET(".")
    fun getRandomUserByCount(@Query("results") count: Int? = 5): Deferred<Response<RandomUserModel>>
}