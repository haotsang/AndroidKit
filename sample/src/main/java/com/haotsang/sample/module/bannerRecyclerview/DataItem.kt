package com.haotsang.sample.module.bannerRecyclerview

import com.haotsang.common.list.ListItem
import com.haotsang.sample.R

data class DataItem(
    val name: String,
    val desc: String,
    override val id: String = System.currentTimeMillis().toString(),
    override val layoutId: Int = R.layout.item_multi_type_data,
    override val contentDescription: String? = null
) : ListItem
