package com.haotsang.collapsing_recyclerview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haotsang.collapsing_recyclerview.core.Data
import com.haotsang.collapsing_recyclerview.core.FoldAdapter
import com.haotsang.collapsing_recyclerview.core.Section

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()

    }

    private fun initViews() {
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
        val rvList = findViewById<RecyclerView>(R.id.rv_list)
        rvList.layoutManager = LinearLayoutManager(this)
        rvList.adapter = adapter
    }

    private fun fakeData(): MutableList<Section> {
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


        val data = ArrayList<Section>()
        arrays.groupBy { it.time }.forEach {
            val headerTitle = it.key
            val items = it.value
            val section = Section(headerTitle, items, true)
            data.add(section)
        }

        return data
    }
}