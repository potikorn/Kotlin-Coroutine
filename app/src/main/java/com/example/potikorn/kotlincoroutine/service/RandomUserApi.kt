package com.example.potikorn.kotlincoroutine.service

import com.example.potikorn.kotlincoroutine.model.RandomUserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {

    @GET(".")
    fun getRandomUser(): Call<RandomUserModel>

    @GET(".")
    fun getRandomUserByCount(@Query("results") count: Int? = 5): Call<RandomUserModel>
}