package com.example.potikorn.kotlincoroutine.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.potikorn.kotlincoroutine.httpmanager.HttpManager
import com.example.potikorn.kotlincoroutine.httpmanager.Result
import com.example.potikorn.kotlincoroutine.model.Info
import com.example.potikorn.kotlincoroutine.model.RandomUserModel
import com.example.potikorn.kotlincoroutine.model.RandomUserResult
import com.example.potikorn.kotlincoroutine.service.RandomUserApi
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.util.concurrent.TimeoutException

class RandomUserViewModel : ViewModel() {

    val retrofit: Retrofit = HttpManager.getRetrofitInstance()
    val service: RandomUserApi

    init {
        service = retrofit.create(RandomUserApi::class.java)
    }

    fun fetchRandomUserList(): MutableLiveData<Result<RandomUserModel>> {
        return baseSingleExecute { service.getRandomUser() }
    }

    fun fetchMultipleList(): MutableLiveData<Result<RandomUserModel>> {
        return baseMultipleExecute {
            val task1 = service.getRandomUser().await()
            val task2 = service.getRandomUserByCount(4).await()
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

    fun <R : Any> baseSingleExecute(block: suspend () -> Deferred<Response<R>>): MutableLiveData<Result<R>> {
        val data = MutableLiveData<Result<R>>()
        GlobalScope.launch(Main) {
            data.value = Result.Loading()
            try {
                val task = withContext(IO) { block().await() }
                if (task.isSuccessful) {
                    data.value = Result.Success(task.body())
                } else {
                    data.value = Result.Failure(task.errorBody())
                }
            } catch (exception: Exception) {
                when (exception) {
                    is HttpException -> {
                        exception.printStackTrace()
                        data.value = Result.Error(exception)
                    }
                    is TimeoutException -> {
                        exception.printStackTrace()
                        data.value = Result.Error(exception)
                    }
                    else -> {
                        exception.printStackTrace()
                        data.value = Result.Error(exception)
                    }
                }
            } finally {
                data.value = Result.Loading(false)
            }
        }
        return data
    }

    fun <R : Any> baseMultipleExecute(
        implementCallback: suspend () -> Result<R>
    ): MutableLiveData<Result<R>> {
        val data = MutableLiveData<Result<R>>()
        GlobalScope.launch(Main) {
            data.value = Result.Loading()
            try {
                val t = implementCallback()
                when (t) {
                    is Result.Success -> data.value = Result.Success(t.data)
                    is Result.Failure -> data.value = Result.Failure(t.data)
                }
            } catch (exception: Exception) {
                when (exception) {
                    is HttpException -> {
                        exception.printStackTrace()
                        data.value = Result.Error(exception)
                    }
                    is TimeoutException -> {
                        exception.printStackTrace()
                        data.value = Result.Error(exception)
                    }
                    else -> {
                        exception.printStackTrace()
                        data.value = Result.Error(exception)
                    }
                }
            } finally {
                data.value = Result.Loading(false)
            }
        }
        return data
    }
}