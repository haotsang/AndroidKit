package com.haotsang.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseVmFragment<T : ViewDataBinding, M : BaseViewModel> : Fragment() {

    abstract val layoutId: Int
    abstract val viewModel: M

    lateinit var databinding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        databinding.lifecycleOwner = viewLifecycleOwner
//        databinding.setVariable(BR.vm, viewModel)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    open fun loadData() {

    }
}