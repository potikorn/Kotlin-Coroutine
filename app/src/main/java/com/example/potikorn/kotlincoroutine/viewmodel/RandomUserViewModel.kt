package com.example.potikorn.kotlincoroutine.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.potikorn.kotlincoroutine.datasource.repository.RandomUserRepository
import com.example.potikorn.kotlincoroutine.httpmanager.Result
import com.example.potikorn.kotlincoroutine.model.RandomUserModel

class RandomUserViewModel : ViewModel() {

    private val repository: RandomUserRepository by lazy { RandomUserRepository() }

    val loadingState = MutableLiveData<Boolean>()
    val randomUserData = MutableLiveData<RandomUserModel>()
    val errorMessage = MutableLiveData<String>()

    fun getOnlyOneUser() {
        repository.fetchRandomOnlyOne().observeForever { result ->
            when (result) {
                is Result.Loading -> loadingState.value = result.isLoading
                is Result.Success -> randomUserData.value = result.data
                is Result.Error -> errorMessage.value = result.exception.message
                is Result.Failure -> errorMessage.value = "on Failure"
            }
        }
    }

    fun getMultipleUsers() {
        repository.fetchMultipleList().observeForever { result ->
            when (result) {
                is Result.Loading -> loadingState.value = result.isLoading
                is Result.Success -> randomUserData.value = result.data
                is Result.Error -> errorMessage.value = result.exception.message
                is Result.Failure -> errorMessage.value = "on Failure"
            }
        }
    }
}