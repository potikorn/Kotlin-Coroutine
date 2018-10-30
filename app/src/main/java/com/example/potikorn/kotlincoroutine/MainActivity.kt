package com.example.potikorn.kotlincoroutine

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvHelloWorld.setOnClickListener {
            GlobalScope.launch {
                setTextAfterDelay(2000, "Hello from a coroutine!")
            }
        }
        btnDownloadBlocking.setOnClickListener {
            startActivity(Intent(this, DownloadBlockingActivity::class.java))
        }
        btnCancelCoroutine.setOnClickListener {
            startActivity(Intent(this, CancelCoroutineActivity::class.java))
        }
    }

    private suspend fun setTextAfterDelay(seconds: Long, text: String) {
        delay(seconds)
        tvHelloWorld.text = text
    }
}
