package com.haotsang.common.utils.ext

import android.content.res.Resources

/**
 * 使用示例 10.dp
 */
inline val Double.dp: Int
    get() = run {
        return toFloat().dp
    }
inline val Int.dp: Int
    get() = run {
        return toFloat().dp
    }
inline val Float.dp: Int
    get() = run {
        val scale: Float = Resources.getSystem().displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }