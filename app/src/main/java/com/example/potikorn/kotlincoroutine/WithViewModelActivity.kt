package com.example.potikorn.kotlincoroutine

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.potikorn.kotlincoroutine.httpmanager.Result
import com.example.potikorn.kotlincoroutine.model.RandomUserModel
import com.example.potikorn.kotlincoroutine.randomuser.RandomUserAdapter
import com.example.potikorn.kotlincoroutine.viewmodel.RandomUserViewModel
import kotlinx.android.synthetic.main.activity_retrofit.*

class WithViewModelActivity : AppCompatActivity() {

    private val randomUserAdapter: RandomUserAdapter by lazy { RandomUserAdapter() }
    private val viewModel: RandomUserViewModel by lazy {
        ViewModelProviders.of(this).get(RandomUserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        rvUserList.apply {
            layoutManager = LinearLayoutManager(this@WithViewModelActivity)
            adapter = randomUserAdapter
        }

        btnFetchSingle.setOnClickListener {
            viewModel.fetchRandomUserList().observe(this, Observer { result ->
                updateUi(result)
            })
        }
        btnFetchMultiple.setOnClickListener {
            viewModel.fetchMultipleList().observe(this, Observer { result ->
                updateUi(result)
            })
        }
    }

    private fun updateUi(result: Result<RandomUserModel>?) {
        when (result) {
            is Result.Loading -> {
                when (result.isLoading) {
                    true -> pbLoading.visibility = View.VISIBLE
                    false -> pbLoading.visibility = View.GONE
                }
            }
            is Result.Success -> randomUserAdapter.setItems(
                result.data?.results ?: mutableListOf()
            )
            is Result.Error -> showToast(result.exception.message ?: "")
            is Result.Failure -> showToast("on Failure")
        }
    }

    private fun showToast(text: String) =
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}