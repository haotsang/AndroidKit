package com.haotsang.common.state

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.haotsang.common.R

class LoadingFragment : Fragment(R.layout.fragment_loading) {

    companion object {
        fun newInstance(hint: String): LoadingFragment {
            return LoadingFragment().apply {
                arguments = Bundle().apply {
                    putString("hint", hint)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hint = arguments?.getString("hint")

    }
}