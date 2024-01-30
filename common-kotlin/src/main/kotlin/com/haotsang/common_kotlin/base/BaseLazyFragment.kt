package com.haotsang.common_kotlin.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseLazyFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    private var initialized: Boolean = false

    open fun initialize() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    override fun onResume() {
        super.onResume()
        if (!initialized && !isHidden) {
            onLazyLoad()
            initialized = true
        }
    }

    open fun onLazyLoad() {
    }
}