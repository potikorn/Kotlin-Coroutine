package com.example.potikorn.kotlincoroutine.randomuser

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.potikorn.kotlincoroutine.R
import com.example.potikorn.kotlincoroutine.model.RandomUserResult
import kotlinx.android.synthetic.main.item_user.view.*

class RandomUserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<RandomUserResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        RandomUserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? RandomUserViewHolder)?.onBindData(items[position])
    }

    fun setItems(randomUserList: MutableList<RandomUserResult>) {
        items.apply {
            clear()
            addAll(randomUserList)
        }
        notifyDataSetChanged()
    }

    inner class RandomUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun onBindData(randomUserResult: RandomUserResult) {
            itemView.apply {
                tvName.text = "${randomUserResult.name?.title} " +
                    "${randomUserResult.name?.first} " +
                    "${randomUserResult.name?.last}"
            }
        }
    }
}