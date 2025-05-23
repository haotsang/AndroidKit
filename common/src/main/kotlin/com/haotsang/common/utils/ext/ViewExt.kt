package com.haotsang.common.utils.ext

import android.graphics.Color
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout


/**
 * Reduces drag sensitivity of [ViewPager2] widget
 * https://gist.github.com/AlShevelev/ea43096e8f66b0ec45a0ec0dd1e8cacc#file-gistfile1-txt
 */
fun ViewPager2.reduceDragSensitivity() {
    val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
    recyclerViewField.isAccessible = true
    val recyclerView = recyclerViewField.get(this) as RecyclerView

    val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
    touchSlopField.isAccessible = true
    val touchSlop = touchSlopField.get(recyclerView) as Int
    touchSlopField.set(recyclerView, touchSlop * 4)       // "8" was obtained experimentally
}

// https://stackoverflow.com/a/53986874
fun RecyclerView.smoothSnapToPosition(position: Int) {
    val smoothScroller = object : LinearSmoothScroller(this.context) {
        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }

        override fun getHorizontalSnapPreference(): Int {
            return SNAP_TO_START
        }

        override fun onStop() {
            super.onStop()
            findViewHolderForAdapterPosition(position)
                ?.itemView?.performClick()
        }
    }
    smoothScroller.targetPosition = position
    layoutManager?.startSmoothScroll(smoothScroller)
}


/**
 * 取消tab item长按提示
 */
fun TabLayout.cancelTooltip() {
    for (i in 0 until this.tabCount) {
        val tab: TabLayout.Tab? = this.getTabAt(i)
        if (tab != null) {
            tab.view.isLongClickable = false
            // 针对android 26及以上需要设置setTooltipText为null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tab.view.tooltipText = null
            }
        }
    }
}

fun TextView.setSpannable(
    text: CharSequence,
    spannableText: String,
    underLine: Boolean = true,
    @ColorInt foregroundColor: Int,
    @ColorInt highlightColor: Int = Color.TRANSPARENT,
    clickAction: (view: View) -> Unit
) {
    this.text = text
    val start = this.text.indexOf(spannableText)
    if (start < 0) {
        return
    }
    val spannableString = SpannableString(this.text)
    spannableString.setSpan(
        ForegroundColorSpan(foregroundColor),
        start, start + spannableText.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )
    spannableString.setSpan(object : ClickableSpan() {
        override fun onClick(view: View) {
            clickAction.invoke(view)
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = underLine
        }
    }, start, start + spannableText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.highlightColor = highlightColor
    this.text = spannableString
    this.movementMethod = LinkMovementMethod.getInstance()
}