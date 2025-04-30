package com.haotsang.sample.module.bannerRecyclerview

import com.haotsang.common.list.ListItem
import com.haotsang.common.list.RecyclerViewListAdapter
import com.haotsang.sample.R

data class SpinnerItem(
    val adapter: RecyclerViewListAdapter? = RecyclerViewListAdapter(),
    val items: List<BannerImageItem>,
    override val id: String = System.currentTimeMillis().toString(),
    override val layoutId: Int = R.layout.item_multi_type_spinner,
    override val contentDescription: String? = null
) : ListItem