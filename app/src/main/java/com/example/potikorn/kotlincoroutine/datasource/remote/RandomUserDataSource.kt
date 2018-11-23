package com.example.potikorn.kotlincoroutine.datasource.remote

import com.example.potikorn.kotlincoroutine.httpmanager.HttpManager
import com.example.potikorn.kotlincoroutine.service.RandomUserApi
import retrofit2.Retrofit

class RandomUserDataSource {

    private val retrofit: Retrofit = HttpManager.getRetrofitInstance()
    private val service: RandomUserApi

    init {
        service = retrofit.create(RandomUserApi::class.java)
    }

    fun randomUserOnlyOne() = service.getRandomUser()

    fun randomUserList(count: Int?) = service.getRandomUserByCount(count)
}