package com.haotsang.common.list

import androidx.recyclerview.widget.AsyncListDiffer

open class BaseAdapter<T : ListItem> : AbstractRecyclerViewAdapter<ListItem>() {

    private val differ = AsyncListDiffer(this, DiffCallBack<T>())

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItem(position: Int): ListItem? {
        return differ.currentList[position]
    }

    override fun getData(): List<ListItem>? {
        return differ.currentList
    }

    fun submitList(newList: List<T>?, runnable: Runnable? = null) {
        differ.submitList(newList, runnable)
    }

}