package com.example.potikorn.kotlincoroutine.httpmanager

import okhttp3.ResponseBody

sealed class Result<out T : Any> {

    class Loading(val isLoading: Boolean = true) : Result<Nothing>()

    class Success<out T : Any>(val data: T?) : Result<T>()

    class Failure<out T : Any>(val data: ResponseBody?) : Result<T>()

    class Error(val exception: Throwable) : Result<Nothing>()
}