package com.example.potikorn.kotlincoroutine

import android.arch.lifecycle.MutableLiveData
import com.example.potikorn.kotlincoroutine.httpmanager.Result
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.TimeoutException

fun <R : Any> baseSingleExecute(block: suspend () -> Deferred<Response<R>>): MutableLiveData<Result<R>> {
    val data = MutableLiveData<Result<R>>()
    GlobalScope.launch(Dispatchers.Main) {
        data.value = Result.Loading()
        try {
            val task = withContext(Dispatchers.IO) { block().await() }
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
    GlobalScope.launch(Dispatchers.Main) {
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