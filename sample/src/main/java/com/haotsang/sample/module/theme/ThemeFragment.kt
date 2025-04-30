package com.haotsang.sample.module.theme

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.SeekBarBindingAdapter.setOnSeekBarChangeListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.haotsang.common.base.BaseViewModel
import com.haotsang.common.base.BaseVmFragment
import com.haotsang.common.list.RecyclerViewListAdapter
import com.haotsang.common.utils.ext.dp
import com.haotsang.sample.R
import com.haotsang.sample.databinding.FragmentThemeBinding


class ThemeFragment: BaseVmFragment<FragmentThemeBinding, BaseViewModel>() {

    override val layoutId: Int = R.layout.fragment_theme
    override val viewModel: BaseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databinding.fragment = this

        databinding.tvIndex.apply {

            val textView = this
            textView.text = "100230"
            // 设置 TextView 的最小和最大字体大小
            val minTextSize = 8.dp
            val maxTextSize = 24.dp

            // 设置 TextView 的最小和最大宽度
            val autoSizeMinWidth = 100.dp
            val autoSizeMaxWidth = 200.dp

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                textView.setAutoSizeTextTypeUniformWithConfiguration(
//
//                )

//                textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            }


//            textView.setAutoSizeTextTypeUniformWithConfiguration(minTextSize, maxTextSize, 4.dp, TypedValue.COMPLEX_UNIT_PX);
            // 配置 TextView 自动调整字体大小
//            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
//                textView,
//                minTextSize.toInt(),
//                maxTextSize.toInt(),
//                autoSizeMinWidth,
//                autoSizeMaxWidth
//            )
        }

        databinding.seekbar.apply {
            min = 1
            max = 100000
            progress = 1
            databinding.tvIndex.text = progress.toString()


            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        databinding.tvIndex.text = progress.toString()


                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }

            })



        }

        val mAdapter = RecyclerViewListAdapter()
        mAdapter.submitList(
            mutableListOf(
                ThemeBindingListItem(title = "121131"),
                ThemeBindingListItem(title = "121131"),
                ThemeBindingListItem(title = "121131"),
                ThemeBindingListItem(title = "121131"),
                ThemeBindingListItem(title = "121131"),
                ThemeBindingListItem(title = "121131"),
                ThemeBindingListItem(title = "121131"),
                ThemeBindingListItem(title = "121131"),
                ThemeBindingListItem(title = "121131"),
            )
        )
        databinding.rvList.run {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = mAdapter
        }
    }


    fun onClick(index: Int) {
        when (index) {
            0 -> AppTheme.update(Themes.Default)
            1 -> AppTheme.update(Themes.Day)
            2 -> AppTheme.update(Themes.Night)
        }
    }



}