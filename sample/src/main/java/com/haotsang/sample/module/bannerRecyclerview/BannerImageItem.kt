package com.haotsang.sample.module.bannerRecyclerview

import com.haotsang.common.list.ListItem
import com.haotsang.sample.R

data class BannerImageItem(
    val image: String?,
    val title: String?,
    override val id: String = System.currentTimeMillis().toString(),
    override val layoutId: Int = R.layout.item_multi_type_banner,
    override val contentDescription: String? = null,
) : ListItem
