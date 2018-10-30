package com.example.potikorn.kotlincoroutine

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // dump simple menu list
    private val menuList = mutableListOf<String>()
    private val menuListAdapter: MenuListAdapter by lazy { MenuListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        rvSimpleMenuList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = menuListAdapter.apply {
                setItems(generateDumpMenu())
                setOnActionListener(object : MenuListAdapter.ActionClickListener<String> {
                    override fun onClick(data: String, position: Int) {
                        Toast.makeText(this@MainActivity, data, Toast.LENGTH_SHORT).show()
                        navigateToOther(position)
                    }
                })
            }
        }
    }

    private fun navigateToOther(position: Int) {
        when (position) {
            0 -> startActivity(Intent(this@MainActivity, CancelCoroutineActivity::class.java))
            1 -> startActivity(Intent(this@MainActivity, DownloadBlockingActivity::class.java))
            2 -> startActivity(Intent(this@MainActivity, AsyncAwaitActivity::class.java))
        }
    }

    private fun generateDumpMenu(): MutableList<String> {
        return menuList.apply {
            add(getString(R.string.action_cancel_coroutine))
            add(getString(R.string.action_go_to_network_sample))
            add(getString(R.string.title_async_await))
        }
    }
}
