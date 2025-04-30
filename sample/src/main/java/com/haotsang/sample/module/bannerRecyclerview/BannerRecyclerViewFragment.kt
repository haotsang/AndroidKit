package com.haotsang.sample.module.bannerRecyclerview

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.haotsang.common.base.BaseBindingFragment
import com.haotsang.common.list.BaseAdapter
import com.haotsang.common.list.ListItem
import com.haotsang.common.list.RecyclerViewListAdapter
import com.haotsang.common.list.VBViewHolder
import com.haotsang.sample.R
import com.haotsang.sample.databinding.FragmentMainListBinding

class BannerRecyclerViewFragment : BaseBindingFragment<FragmentMainListBinding>(R.layout.fragment_main_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RecyclerViewListAdapter()
        adapter.submitList(generateList())

        val spanCount = 60
        val lm = GridLayoutManager(context, spanCount).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when(adapter.getItem(position)) {
                        is ViewPageItem, is SpinnerItem, is ListHeaderItem -> {
                            spanCount / 1
                        }
                        is BannerImageItem -> {
                            spanCount / 3
                        }
                        else -> {
                            spanCount / 4
                        }
                    }
                }
            }
        }

        mBinding?.rvList?.apply {
            layoutManager = lm
            this.adapter = adapter
        }
    }

    private fun generateList(): List<ListItem> {
        return listOf(
            ListHeaderItem(title = "可滑动的ViewPager banner"),
            ViewPageItem(items = listOf(
                ViewPageImageItem(image = generateRandomImageUrl()),
                ViewPageImageItem(image = generateRandomImageUrl()),
                ViewPageImageItem(image = generateRandomImageUrl()),
                ViewPageImageItem(image = generateRandomImageUrl()),
                ViewPageImageItem(image = generateRandomImageUrl()),
                ViewPageImageItem(image = generateRandomImageUrl()),
            )),
            ListHeaderItem(title = "可滑动的RecyclerView banner"),
            SpinnerItem(items = listOf(
                BannerImageItem(title = "", image = generateRandomImageUrl()),
                BannerImageItem(title = "", image = generateRandomImageUrl()),
                BannerImageItem(title = "", image = generateRandomImageUrl()),
                BannerImageItem(title = "", image = generateRandomImageUrl()),
                BannerImageItem(title = "", image = generateRandomImageUrl()),
                BannerImageItem(title = "", image = generateRandomImageUrl()),
            )),
            ListHeaderItem(title = "固定的banner item"),
            BannerImageItem(title = "", image = generateRandomImageUrl()),
            BannerImageItem(title = "", image = generateRandomImageUrl()),
            BannerImageItem(title = "", image = generateRandomImageUrl()),

            DataItem(name = "1111", desc = "2222"),
            DataItem(name = "1111", desc = "2222"),
            DataItem(name = "1111", desc = "2222"),
            DataItem(name = "1111", desc = "2222"),
            DataItem(name = "1111", desc = "2222"),
            DataItem(name = "1111", desc = "2222"),
            DataItem(name = "1111", desc = "2222"),
            DataItem(name = "1111", desc = "2222"),
            DataItem(name = "1111", desc = "2222"),
            DataItem(name = "1111", desc = "2222"),
            DataItem(name = "1111", desc = "2222"),
            DataItem(name = "1111", desc = "2222"),
            DataItem(name = "1111", desc = "2222"),
        )
    }

    private fun generateRandomImageUrl(): String {
        val randomId = (1..50).random() // 模拟随机图片 ID
        return "https://picsum.photos/id/$randomId/300/200"
    }

}