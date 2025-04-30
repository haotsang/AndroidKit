package com.haotsang.sample.module.theme

import com.haotsang.common.list.ListItem
import com.haotsang.sample.R

data class ThemeBindingListItem(
    override val id: String = System.currentTimeMillis().toString(),
    override val layoutId: Int = R.layout.item_theme_databinding,
    override val contentDescription: String? = null,
    val title: String
) : ListItem