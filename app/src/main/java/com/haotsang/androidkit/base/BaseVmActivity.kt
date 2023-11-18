package com.haotsang.androidkit.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding


/**
 *  示例
 *  ```
 *  class XXXActivity : BaseVmActivity<XXXBinding, XXXViewModel>(XXXBinding::inflate) {
 *      override fun viewModelClass(): Class<XXXViewModel> = XXXViewModel::class.java
 *  }
 *  ```
 */
abstract class BaseVmActivity<VB : ViewBinding, VM : ViewModel>(inflate: (LayoutInflater) -> VB) : BaseActivity<VB>(inflate) {

    protected open lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        observe()
        initView()
        initData()
        setListener()
    }

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        mViewModel = ViewModelProvider(this)[viewModelClass()]
    }

    /**
     * 获取ViewModel的class
     */
    protected abstract fun viewModelClass(): Class<VM>

    /**
     * 订阅，有逻辑的话，复写的时候super不要去掉
     */
    open fun observe() {}

    /**
     * 初始化view相关
     */
    open fun initView() {}

    /**
     * 初始化data相关
     */
    open fun initData() {}

    /**
     * 设置监听
     */
    open fun setListener() {}


}