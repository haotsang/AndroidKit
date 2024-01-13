package com.haotsang.common_kotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 *  示例
 *  ```
 *  class XXXActivity : BaseActivity<XXXBinding>(XXXBinding::inflate) {
 *  }
 *  ```
 */

abstract class BaseActivity<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) : AppCompatActivity() {

    protected open lateinit var mBinding: VB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = inflate(layoutInflater)
        setContentView(mBinding.root)
        initialize()
    }

    /**
     * 命名与子类要区分，否则会先调用BaseActivity中同名方法，再调用BaseVmActivity中同名方法，可能会出现调用顺序导致的错误
     */
    open fun initialize() {}

}