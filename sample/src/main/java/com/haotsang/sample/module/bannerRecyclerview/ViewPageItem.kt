package com.haotsang.sample.module.bannerRecyclerview

import com.haotsang.common.list.ListItem
import com.haotsang.sample.R

data class ViewPageItem(
    val items: List<ViewPageImageItem>,
    override val id: String = System.currentTimeMillis().toString(),
    override val layoutId: Int = R.layout.item_multi_type_viewpager,
    override val contentDescription: String? = null
) : ListItem

data class ViewPageImageItem(
    val image: String,
    override val id: String = System.currentTimeMillis().toString(),
    override val layoutId: Int = R.layout.item_multi_type_viewpager_image,
    override val contentDescription: String? = null

) : ListItem