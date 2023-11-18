package com.haotsang.androidkit.utils.ext

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * 获取颜色
 */
@JvmName("getColor")
fun getResColor(@ColorRes colorRes: Int) = ContextCompat.getColor(AndUtil.appContext, colorRes)

/**
 * 获取图片资源
 */
@JvmName("getDrawable")
fun getResDrawable(@DrawableRes drawableRes: Int) =
    ContextCompat.getDrawable(AndUtil.appContext, drawableRes)
