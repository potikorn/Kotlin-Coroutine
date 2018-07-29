package com.example.potikorn.kotlincoroutine

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvHelloWorld.setOnClickListener {
            launch(UI) {
                setTextAfterDelay(2, "Hello from a coroutine!")
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
        delay(seconds, TimeUnit.SECONDS)
        tvHelloWorld.text = text
    }
}
