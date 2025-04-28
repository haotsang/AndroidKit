package com.haotsang.common.utils

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter

/**
 * databinding 点击事件添加防抖
 * <ImageButton
 *     ...
 *     android:onClickListener="@{() -> listener.onTogglePwd()}" />
 *
 *     android:onClick="@{vm::onTogglePwd}"
 */
object ViewThrottleBindingAdapter {

    @BindingAdapter("android:onClickListener")
    @JvmStatic fun setViewOnClickListener(view: View, callback: View.OnClickListener) {
        view.setOnClickListener(ThrottleOnClickListener(callback))
    }

    @BindingAdapter("android:onClick")
    @JvmStatic fun setViewOnClick(view: View, callback: View.OnClickListener) {
        view.setOnClickListener(ThrottleOnClickListener(callback))
    }

    /** 原始OnClickListener的包装 */
    private class ThrottleOnClickListener(
        private val callback: View.OnClickListener
    ) : View.OnClickListener {

        // 上次点击时间
        private var mLastTime = 0L

        override fun onClick(v: View?) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - mLastTime >= CLICK_THRESHOLD) {
                mLastTime = currentTime
                // 调用点击方法
                callback.onClick(v)
                Log.d("ViewThrottleBindingAdapter", "onClick: ${v?.id}")
            }
        }

        companion object {
            // 1秒之类的点击过滤掉
            private const val CLICK_THRESHOLD = 1000
        }
    }
}