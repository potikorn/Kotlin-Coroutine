package com.example.potikorn.kotlincoroutine

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.menu_item.view.*

class MenuListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<String>()
    private var actionCallback: ActionClickListener<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MenuListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.menu_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MenuListViewHolder)?.onBindData(items[position], actionCallback)
    }

    fun setOnActionListener(callback: ActionClickListener<String>) {
        actionCallback = callback
    }

    fun setItems(menuList: MutableList<String>) {
        items.clear()
        items.addAll(menuList)
        notifyDataSetChanged()
    }

    inner class MenuListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBindData(
            string: String,
            actionListener: ActionClickListener<String>?
        ) {
            itemView.apply {
                setOnClickListener { actionListener?.onClick(string, adapterPosition) }
                tvMenu.text = string
            }
        }
    }

    interface ActionClickListener<DATA> {
        fun onClick(data: DATA, position: Int) {}
    }
}