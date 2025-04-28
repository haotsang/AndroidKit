package com.haotsang.sample

import android.app.Application
import com.haotsang.common.base.BaseViewModel
import com.haotsang.common.utils.AppViewModelHolder
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    private val baseViewModel by lazy {
        AppViewModelHolder.getViewModelProvider().get(BaseViewModel::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        AppViewModelHolder.initialize(this)


    }

    override fun onTerminate() {
        super.onTerminate()
        AppViewModelHolder.clear()
    }
}