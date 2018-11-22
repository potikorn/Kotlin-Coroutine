package com.example.potikorn.kotlincoroutine

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.potikorn.kotlincoroutine.httpmanager.HttpManager
import com.example.potikorn.kotlincoroutine.model.RandomUserModel
import com.example.potikorn.kotlincoroutine.randomuser.RandomUserAdapter
import com.example.potikorn.kotlincoroutine.service.RandomUserApi
import kotlinx.android.synthetic.main.activity_retrofit.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class RetrofitActivity : AppCompatActivity() {

    private val randomUserAdapter: RandomUserAdapter by lazy { RandomUserAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        rvUserList.apply {
            layoutManager = LinearLayoutManager(this@RetrofitActivity)
            adapter = randomUserAdapter
        }

        val retrofit = HttpManager.getRetrofitInstance()
        val service = retrofit.create(RandomUserApi::class.java)

        GlobalScope.launch(Main) {
            pbLoading.visibility = View.VISIBLE
            coroutineScope {
                val task1 = withContext(IO) { service.getRandomUserByCount(4).await() }
                val task2 = withContext(IO) { service.getRandomUser().await() }
//                awaitAll(service.getRandomUser(), service.getRandomUser())
                checkResult(task1, task2)
            }
        }
    }

    private fun checkResult(
        task1: Response<RandomUserModel>,
        task2: Response<RandomUserModel>
    ) {
        pbLoading.visibility = View.GONE
        when {
            task1.isSuccessful and task2.isSuccessful -> {
                // add value from task2 if isNotEmpty
                task2.body()?.results?.let {
                    task1.body()
                        ?.results?.add(it.first().copy(name = it.first().name?.copy(last = "${it.first().name?.last} (Append)")))
                }
                randomUserAdapter.setItems(task1.body()?.results ?: mutableListOf())
                Log.i(this::class.java.simpleName, task2.body().toString())
            }
            else -> {
                Log.i(this::class.java.simpleName, "On Error Something...")
            }
        }
    }
}