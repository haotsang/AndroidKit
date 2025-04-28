package com.haotsang.common.view

import android.content.Context
import android.widget.FrameLayout

class StatusView(context: Context) : FrameLayout(context) {

    enum class Status {
        LOADING,
        EMPTY,
        ERROR,
        SUCCESS,
    }


}