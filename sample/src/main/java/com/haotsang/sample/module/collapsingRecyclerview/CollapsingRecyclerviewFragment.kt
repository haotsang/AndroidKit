package com.haotsang.sample.module.collapsingRecyclerview

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haotsang.common.base.BaseBindingFragment
import com.haotsang.sample.R
import com.haotsang.sample.databinding.ActivityMainBinding

class CollapsingRecyclerviewFragment : BaseBindingFragment<ActivityMainBinding>(R.layout.activity_main) {

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