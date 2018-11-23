package com.example.potikorn.kotlincoroutine.datasource.repository

import android.arch.lifecycle.MutableLiveData
import com.example.potikorn.kotlincoroutine.baseMultipleExecute
import com.example.potikorn.kotlincoroutine.baseSingleExecute
import com.example.potikorn.kotlincoroutine.datasource.remote.RandomUserDataSource
import com.example.potikorn.kotlincoroutine.httpmanager.Result
import com.example.potikorn.kotlincoroutine.model.Info
import com.example.potikorn.kotlincoroutine.model.RandomUserModel
import com.example.potikorn.kotlincoroutine.model.RandomUserResult

class RandomUserRepository {

    private val remoteDataSource: RandomUserDataSource = RandomUserDataSource()

    fun fetchRandomOnlyOne(): MutableLiveData<Result<RandomUserModel>> {
        return baseSingleExecute { remoteDataSource.randomUserOnlyOne() }
    }

    fun fetchMultipleList(): MutableLiveData<Result<RandomUserModel>> {
        return baseMultipleExecute {
            val task1 = remoteDataSource.randomUserOnlyOne().await()
            val task2 = remoteDataSource.randomUserList(20).await()
            when {
                task1.isSuccessful && task2.isSuccessful -> {
                    val newData = mutableListOf<RandomUserResult>()
                    newData.addAll(task1.body()?.results ?: mutableListOf())
                    newData.addAll(task2.body()?.results ?: mutableListOf())
                    val model = RandomUserModel(results = newData, info = Info("", 0, 0, ""))
                    return@baseMultipleExecute Result.Success(model)
                }
                task1.isSuccessful && !task2.isSuccessful -> {
                    return@baseMultipleExecute Result.Failure(task2.errorBody())
                }
                else -> return@baseMultipleExecute Result.Success(
                    RandomUserModel(mutableListOf(), Info("", 0, 0, ""))
                )
            }
        }
    }

}