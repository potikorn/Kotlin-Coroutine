package com.example.potikorn.kotlincoroutine

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.acitvity_download_blocking.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.cancelAndJoin
import kotlinx.coroutines.experimental.launch

class CancelCoroutineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitvity_download_blocking)

        val startTime = System.currentTimeMillis()
        val job = launch {
            var nextPrintTime = startTime
            var i = 0
            while (isActive) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    launch(UI) { tvData.text = i++.toString() }
                    nextPrintTime += 1000L
                }
            }
        }
        fabOne.setOnClickListener {
            launch(UI) {
                job.cancelAndJoin()
                tvData.text = "Cancelled"
            }
        }
    }
}