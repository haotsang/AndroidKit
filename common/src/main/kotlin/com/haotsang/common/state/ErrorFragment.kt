package com.haotsang.common.state

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.haotsang.common.R

class ErrorFragment : Fragment(R.layout.fragment_error) {

    companion object {
        fun newInstance(error: String?, onRetryListener: (() -> Unit)?): ErrorFragment {
            return ErrorFragment().apply {
                mOnRetryListener = onRetryListener
                arguments = Bundle().apply {
                    putString("error", error)
                }
            }
        }
    }


    @Volatile
    internal var mOnRetryListener: (() -> Unit)? = null
        set(value) {
            field = value
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mOnRetryListener == null) {
            view.setOnClickListener {
                mOnRetryListener?.invoke()
            }
        }

        val msg = arguments?.getString("error")

    }
}