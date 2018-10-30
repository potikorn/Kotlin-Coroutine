package com.example.potikorn.kotlincoroutine

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.acitvity_download_blocking.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DownloadBlockingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitvity_download_blocking)
        fabOne.setOnClickListener {
            GlobalScope.launch {
                val data = downloadDataBlocking()
                // process data on the UI thread
                launch(Dispatchers.Main) { tvData.text = data }
            }
        }
    }

    private suspend fun downloadDataBlocking(): String {
        return suspendCoroutine { cont ->
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts")
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    cont.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    cont.resume(response.body()?.string() ?: "")
                }
            })
        }
    }
}