package com.haotsang.sample.theme

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haotsang.common_kotlin.utils.ext.dp
import com.haotsang.sample.R
import com.haotsang.sample.databinding.FragmentThemeBinding


class ThemeFragment : Fragment(R.layout.fragment_theme) {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DataBindingUtil.bind<FragmentThemeBinding>(view)!!

        binding.lifecycleOwner = this

        binding.tvIndex.apply {

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

        binding.seekbar.apply {
            min = 1
            max = 100000
            progress = 1
            binding.tvIndex.text = progress.toString()


            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        binding.tvIndex.text = progress.toString()


                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }

            })



        }


        binding.btnDefault.setOnClickListener { AppTheme.update(Themes.Default) }
        binding.btnDay.setOnClickListener { AppTheme.update(Themes.Day) }
        binding.btnNight.setOnClickListener { AppTheme.update(Themes.Night) }

        binding.btnDefault.bringToFront()
        binding.btnDay.bringToFront()
        binding.btnNight.bringToFront()

        val mAdapter = MyAdapter()
        mAdapter.setOnItemClickListener { adapter, view, position ->
//            ThemeAdapter.forceRecycler(binding.rvList, true)
//            adapter.notifyDataSetChanged()
        }
        mAdapter.setNewInstance(mutableListOf<String>("121131", "121131"))
        binding.rvList.run {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }
    }





}