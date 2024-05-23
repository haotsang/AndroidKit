package com.haotsang.sample.module.state

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.haotsang.common_kotlin.base.BaseFragment
import com.haotsang.common_kotlin.base.StatusData
import com.haotsang.common_kotlin.utils.ext.collectWhile
import com.haotsang.sample.R
import com.haotsang.sample.databinding.FragmentThemeBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StateFragment : BaseFragment<FragmentThemeBinding>(FragmentThemeBinding::inflate) {

    private val _meetings = MutableStateFlow<StatusData<List<String>>>(StatusData.Idle)
    internal val meetings = _meetings.asStateFlow()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {

        }
        _meetings.value = StatusData.Loading
//        _meetings.value = StatusData.Error(Exception())
//
//        _meetings.value = StatusData.Success(listOf("1", "2"))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvList = mBinding!!.rvGroup

        meetings.collectWhile(this, Lifecycle.State.RESUMED) {
            when (it) {
                is StatusData.Loading -> {
                    rvList.visibility = View.GONE
                    showLoading(R.id.root)
                }
                is StatusData.Error -> {
                    rvList.visibility = View.GONE
                    showError(containerViewId = R.id.root, error = it.exception.message)
                }
                is StatusData.Empty -> {
                    rvList.visibility = View.GONE
                    showEmpty(R.id.root)
                }
                is StatusData.Success<List<String>> -> {
                    hideLoading()
                    hideEmpty()
                    hideError()

                    rvList.visibility = View.VISIBLE
                    //set data
                    val list:List<String> = it.data
                }
                is StatusData.Idle -> {
                    hideLoading()
                }
            }
        }

    }

    override fun onRetry() {
        super.onRetry()

    }
}