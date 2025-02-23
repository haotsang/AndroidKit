package com.haotsang.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.haotsang.common.R
import com.haotsang.common.state.EmptyFragment
import com.haotsang.common.state.ErrorFragment
import com.haotsang.common.state.LoadingFragment

abstract class BaseFragment<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) : Fragment() {

    private var binding: VB? = null
    protected open val mBinding get() = binding

    open fun initialize() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(layoutInflater)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isClickable = true // 防止view穿透
        initialize()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }



    open fun hideLoading() {
        removeFragment(LoadingFragment::class.java.simpleName)
    }

    /**
     * @param containerViewId 展示loading 的父view
     */
    open fun showLoading(
        containerViewId: Int = android.R.id.content,
        hint: String = getString(R.string.loading),
    ) {
        hideEmpty()
        hideError()

        addFragment(
            containerViewId,
            LoadingFragment.newInstance(hint)
        )
    }

    /**
     * @param containerViewId 展示error 的父view
     * @param error error 的类型
     */
    open fun showError(
        containerViewId: Int = android.R.id.content,
        error: String?,
    ) {
        hideLoading()

        val errorFragment = ErrorFragment.newInstance(error, onRetryListener = {
            onRetry()
        })
        addFragment(containerViewId, errorFragment)
    }

    open fun hideError() {
        removeFragment(ErrorFragment::class.java.simpleName)
    }

    open fun showEmpty(
        containerViewId: Int = android.R.id.content,
        hint: String = getString(R.string.empty),
    ) {
        hideLoading()

        addFragment(containerViewId, EmptyFragment.newInstance(hint))
    }

    open fun hideEmpty() {
        removeFragment(EmptyFragment::class.java.simpleName)
    }

    open fun onRetry() {}


    private fun addFragment(
        containerViewId: Int = android.R.id.content,
        fragment: Fragment,
        tag: String? = fragment.javaClass.simpleName
    ): Boolean {
        childFragmentManager.beginTransaction()
            .add(
                containerViewId,
                fragment,
                tag
            )
            .commitNowAllowingStateLoss()
        return true
    }

    private fun removeFragment(tag: String?): Boolean {
        with(childFragmentManager) {
            findFragmentByTag(tag)?.let {
                beginTransaction().remove(it).commitNowAllowingStateLoss()
                return true
            }
        }

        return false
    }
}