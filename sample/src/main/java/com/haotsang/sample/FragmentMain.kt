package com.haotsang.sample

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.haotsang.common.base.BaseBindingAdapter
import com.haotsang.common.base.BaseBindingViewHolder
import com.haotsang.common.base.BaseFragment
import com.haotsang.sample.databinding.FragmentMainBinding
import com.haotsang.sample.databinding.ItemBaseQuickAdapterBinding
import com.haotsang.sample.module.collapsingRecyclerview.CollapsingRecyclerviewFragment
import com.haotsang.sample.module.component.BlurOverlayFragment
import com.haotsang.sample.module.component.WickedGradientDrawableFragment
import com.haotsang.sample.module.theme.ThemeFragment

class FragmentMain : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    override fun initialize() {
        super.initialize()

        val list = listOf(
            "RulesEngine",
            "Theme",
            "WickedGradientDrawable",
            "BlurOverlay",
            "CollapsingRecyclerview",
        )
        val mAdapter = ListAdapter(requireContext(), this)
        mAdapter.setData(list)
        mAdapter.setOnItemClickListener { s, i ->
            val activity = activity as? MainActivity ?: return@setOnItemClickListener
            when (i) {
//                0 -> switchFragment(RuleFragment())
                1 -> activity.switchFragment(ThemeFragment())
                2 -> activity.switchFragment(WickedGradientDrawableFragment())
                3 -> activity.switchFragment(BlurOverlayFragment())
                4 -> activity.switchFragment(CollapsingRecyclerviewFragment())


                else -> {
                }
            }
        }
        mBinding?.rvList?.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
    }

    private class ListAdapter(context: Context, lifecycleOwner: LifecycleOwner) :
        BaseBindingAdapter<String, ItemBaseQuickAdapterBinding>(context, lifecycleOwner) {
        override fun getLayout(viewType: Int): Int {
            return R.layout.item_base_quick_adapter
        }
        override fun onBindData(
            binding: ItemBaseQuickAdapterBinding,
            holder: BaseBindingViewHolder,
            data: String,
            position: Int
        ) {
            binding.text1.text = data
        }
    }

}