package com.haotsang.common.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class MarqueeTextView : AppCompatTextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun isFocused(): Boolean {
        return true
        //        自定义设置让focusable为true
        //        这个方法相当于在layout中
        //        android:focusable="true"
        //        android:focusableInTouchMode="true"
    }

    /**
     * 详情见TextView中setSelected方法
     * TextView中setSelected方法调该方法，返回false才有跑马灯效果
     * @return 返回false
     */
    override fun isSelected(): Boolean {
        return false
    }

    init {
        ellipsize = TextUtils.TruncateAt.MARQUEE
        isFocusable = true
        isFocusableInTouchMode = true
        marqueeRepeatLimit = -1 /*Infinity*/
        isSingleLine = true
    }
}