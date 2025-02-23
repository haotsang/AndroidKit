package com.haotsang.common.state

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.haotsang.common.R

class EmptyFragment : Fragment(R.layout.fragment_empty) {

    companion object {
        fun newInstance(hint: String): EmptyFragment {
            return EmptyFragment().apply {
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