package com.example.potikorn.kotlincoroutine

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.acitvity_download_blocking.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CancelCoroutineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitvity_download_blocking)

        val startTime = System.currentTimeMillis()
        val job = GlobalScope.launch {
            var nextPrintTime = startTime
            var i = 0
            while (isActive) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    launch(Dispatchers.Main) { tvData.text = i++.toString() }
                    nextPrintTime += 1000L
                }
            }
        }
        fabOne.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                job.cancelAndJoin()
                tvData.text = "Cancelled"
            }
        }
    }
}