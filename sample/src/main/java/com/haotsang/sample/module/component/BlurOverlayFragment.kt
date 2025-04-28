package com.haotsang.sample.module.component

import com.bumptech.glide.Glide
import com.haotsang.common.base.BaseFragment
import com.haotsang.sample.R
import com.haotsang.sample.databinding.FragmentBlurOverlayBinding

class BlurOverlayFragment :
    BaseFragment<FragmentBlurOverlayBinding>(FragmentBlurOverlayBinding::inflate) {

    override fun initialize() {
        super.initialize()

        Glide.with(this).load(
            R.drawable.banner
        ).into(mBinding!!.ivNormal)

        Glide.with(this).load(
            R.drawable.banner
        ).into(mBinding!!.ivBlur)

    }
}