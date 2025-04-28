package com.haotsang.common.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import com.haotsang.common.R

abstract class AbsPopupWindow(val context: Context, layoutResId: Int) : PopupWindow() {

    init {
        LayoutInflater.from(context).inflate(
            layoutResId, null
        ).also {
            contentView = it
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.MATCH_PARENT
            initView(it)
        }

    }

    abstract fun initView(view: View)


    override fun setContentView(contentView: View?) {
        super.setContentView(contentView)
        contentView?.apply {
            setBackgroundColor(ContextCompat.getColor(context, R.color.black_50))
            setOnClickListener {
                dismiss()
            }
        }
        context.apply {
            if (this is ComponentActivity) {
                onBackPressedDispatcher.addCallback(this) {
                    dismiss()
                }
            }
        }
    }

}