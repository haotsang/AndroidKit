package com.haotsang.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.FrameLayout
import kotlin.math.abs

/**
 * 处理水平滚动的 child (例如 ViewPager2) 与其可以垂直滚动的父布局 (例如 RecyclerView) 之间的 touch 滚动优先级
 */
class NestedScrollHost : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var startX = 0f
    private var startY = 0f
    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        // 避免点击事件穿透到底层 RecyclerView
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x
                startY = ev.y
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = ev.x - startX
                val dy = ev.y - startY

                if (abs(dx) > abs(dy) && abs(dx) > touchSlop) {
                    // 水平滑动，ViewPager2 处理
                    parent.requestDisallowInterceptTouchEvent(true)
                } else if (abs(dy) > touchSlop) {
                    // 垂直滑动，交给父容器
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }
}