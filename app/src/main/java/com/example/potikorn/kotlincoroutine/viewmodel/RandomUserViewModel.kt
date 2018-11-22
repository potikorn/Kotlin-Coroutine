package com.example.potikorn.kotlincoroutine.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.potikorn.kotlincoroutine.httpmanager.HttpManager
import com.example.potikorn.kotlincoroutine.httpmanager.Result
import com.example.potikorn.kotlincoroutine.model.RandomUserModel
import com.example.potikorn.kotlincoroutine.service.RandomUserApi
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception
import java.util.concurrent.TimeoutException

class RandomUserViewModel : ViewModel() {

    val retrofit: Retrofit = HttpManager.getRetrofitInstance()
    val service: RandomUserApi

    init {
        service = retrofit.create(RandomUserApi::class.java)
    }

    val randomUserList = fetchRandomUserList()

    fun fetchRandomUserList(): MutableLiveData<Result<RandomUserModel>> {
        return baseSingleExecute { service.getRandomUser() }
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
}