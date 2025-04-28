package com.haotsang.common.utils

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

object AppViewModelHolder: ViewModelStoreOwner {

    lateinit var mViewModelStore: ViewModelStore
    lateinit var defaultFactory: ViewModelProvider.Factory

    fun initialize(app: Application) {
        mViewModelStore = ViewModelStore()
        defaultFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(app)
    }

    override val viewModelStore: ViewModelStore
        get() = mViewModelStore

    fun getViewModelProvider(factory: ViewModelProvider.Factory = defaultFactory): ViewModelProvider {
        return ViewModelProvider(this, factory)
    }

    fun clear() {
        Log.d("AppViewModelHolder", "ViewModelStore clear")
        mViewModelStore.clear()
    }


}

