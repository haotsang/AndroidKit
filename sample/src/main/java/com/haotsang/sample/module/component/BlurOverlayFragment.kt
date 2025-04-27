package com.haotsang.sample.module.component

import com.bumptech.glide.Glide
import com.haotsang.common.base.BaseFragment
import com.haotsang.sample.databinding.FragmentBlurOverlayBinding

class BlurOverlayFragment :
    BaseFragment<FragmentBlurOverlayBinding>(FragmentBlurOverlayBinding::inflate) {


    override fun initialize() {
        super.initialize()

        Glide.with(this).load(
            "https://s1.aigei.com/prevfiles/6eaa102496aa40e68548d134dd0f2c55.gif?e=2051020800&token=P7S2Xpzfz11vAkASLTkfHN7Fw-oOZBecqeJaxypL:yQkYPd0E83uN8zNCCAH61fMR-Cs="
        ).into(mBinding!!.ivNormal)

        Glide.with(this).load(
            "https://s1.aigei.com/prevfiles/6eaa102496aa40e68548d134dd0f2c55.gif?e=2051020800&token=P7S2Xpzfz11vAkASLTkfHN7Fw-oOZBecqeJaxypL:yQkYPd0E83uN8zNCCAH61fMR-Cs="
        ).into(mBinding!!.ivBlur)



    }
}