package com.haotsang.sample.module.component

import com.haotsang.common.base.BaseFragment
import com.haotsang.sample.databinding.FragmentRippleBackgroundBinding

class RippleBackgroundFragment : BaseFragment<FragmentRippleBackgroundBinding>(
    FragmentRippleBackgroundBinding::inflate
) {

    override fun initialize() {

        mBinding?.ripple?.startRippleAnimation()

    }


}