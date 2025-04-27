package com.haotsang.sample.module.theme

import android.content.Context
import androidx.lifecycle.LifecycleOwner

import com.haotsang.common.base.BaseBindingAdapter
import com.haotsang.common.base.BaseBindingViewHolder
import com.haotsang.sample.R
import com.haotsang.sample.databinding.ItemDatabindBinding


class MyAdapter(private val  context: Context, private val lifecycle: LifecycleOwner) :
    BaseBindingAdapter<String, ItemDatabindBinding>(context, lifecycle) {

    override fun getLayout(viewType: Int): Int {
        return R.layout.item_databind
    }

    override fun onBindData(
        binding: ItemDatabindBinding,
        holder: BaseBindingViewHolder,
        data: String,
        position: Int
    ) {
        binding.tvTitle.text = data
    }

}

