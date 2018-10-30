package com.example.potikorn.kotlincoroutine

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.acitivity_async_await.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

class AsyncAwaitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_async_await)
        fabOne.setOnClickListener {
            GlobalScope.launch {
                val deferred = async {
                    val client = OkHttpClient()
                    val request = Request.Builder()
                        .url("https://jsonplaceholder.typicode.com/posts")
                        .build()
                    client.newCall(request).execute()
                }
                Log.e(
                    AsyncAwaitActivity::class.java.simpleName,
                    deferred.await().isSuccessful.toString()
                )
                launch(Dispatchers.Main) { tvData.text = deferred.await().body()?.string() }
            }
        }
    }
}