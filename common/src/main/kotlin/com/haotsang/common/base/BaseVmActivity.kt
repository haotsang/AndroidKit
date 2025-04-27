package com.haotsang.common.base

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Display
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseVmActivity<T : ViewDataBinding, M : BaseViewModel> : AppCompatActivity() {

    abstract val layoutId: Int
    abstract val viewModel: M?
    protected open val fragmentContainerId: Int = 0

    val databinding: T by lazy {
        DataBindingUtil.setContentView(this, layoutId)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding.lifecycleOwner = this
        databinding.setVariable(BR.vm, viewModel)
    }


}