package com.haotsang.sample.module.collapsingRecyclerview

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.haotsang.common.base.BaseBindingAdapter
import com.haotsang.common.base.BaseBindingFragment
import com.haotsang.common.base.BaseBindingViewHolder
import com.haotsang.common.utils.ToastService
import com.haotsang.common.utils.ext.reduceDragSensitivity
import com.haotsang.common.utils.showToast
import com.haotsang.sample.R
import com.haotsang.sample.databinding.FragmentCollapseListBinding
import com.haotsang.sample.databinding.ItemBannerBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CollapsingRecyclerviewFragment : BaseBindingFragment<FragmentCollapseListBinding>(R.layout.fragment_collapse_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FoldAdapter()
        adapter.apply {
            adapter.setData(fakeData())
            setOnItemClickListener { view, position, item ->
            }
            setOnItemChildClickListener { viewId, item ->
                when (viewId) {
                }
            }
        }
        mBinding?.rvList?.layoutManager = LinearLayoutManager(requireContext())
        mBinding?.rvList?.adapter = adapter


        initBanner()

        lifecycleScope.launch {
            ToastService.shownToastFlow.collectLatest {
                val toast = it?:return@collectLatest
                Toast.makeText(requireContext(), toast.text, 0).show()
            }
        }

    }

    private fun  initBanner() {
        mBinding?.clDetial?.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            reduceDragSensitivity()

            // 设置页面转换动画
            setPageTransformer { page, position ->
                page.translationX = -position
            }

            this.adapter = object : BaseBindingAdapter<String, ItemBannerBinding>(requireContext(), this@CollapsingRecyclerviewFragment) {
                override fun getLayout(viewType: Int): Int =R.layout.item_banner

                override fun onBindData(
                    binding: ItemBannerBinding,
                    holder: BaseBindingViewHolder,
                    data: String,
                    position: Int
                ) {
                    Glide.with(binding.bannerImage)
                        .load(data)
                        .centerCrop()
                        .into(binding.bannerImage)
                }

            }.apply {
                setData(
                    listOf(
                        "https://th.bing.com/th/id/OSK.HEROj12u65SRf5tYUscsIZoUZBr-fXkq8jI_4-gHD-N2sMQ?w=384&h=228&c=1&rs=2&o=6&dpr=1.5&pid=SANGAM",
                        "https://bkimg.cdn.bcebos.com/pic/0bd162d9f2d3572c11dfb6336541742762d0f603659b?x-bce-process=image/format,f_auto/quality,Q_70/resize,m_lfit,limit_1,w_536",
                        "https://developer.android.google.cn/images/home/io-agenda-shapes.png"
                    )
                )

                setOnItemClickListener { s, i ->
                    showToast("" + i)

                }

            }
        }
    }

    private fun fakeData(): MutableList<Section<Data>> {
        val arrays = arrayOf(
            Data("2024-5-4", "4", "4-1"),
            Data("2024-5-4", "4", "4-2"),
            Data("2024-5-4", "4", "4-3"),
            Data("2024-5-4", "4", "4-4"),
            Data("2024-5-4", "4", "4-5"),

            Data("2024-5-3", "3", "3-1"),
            Data("2024-5-3", "3", "3-2"),
            Data("2024-5-3", "3", "3-3"),
            Data("2024-5-3", "3", "3-4"),

            Data("2024-5-2", "2", "2-1"),
            Data("2024-5-2", "2", "2-2"),
            Data("2024-5-2", "2", "2-3"),

            Data("2024-5-1", "1", "1-1"),
            Data("2024-5-1", "1", "1-2"),
        )


        val data = ArrayList<Section<Data>>()
        arrays.groupBy { it.time }.forEach {
            val headerTitle = it.key
            val items = it.value
            val section =
                Section<Data>(headerTitle, items, true)
            data.add(section)
        }

        return data
    }
}