package com.haotsang.sample

import android.content.Intent
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.haotsang.common_kotlin.base.BaseActivity
import com.haotsang.sample.databinding.ActivityMainBinding
import com.haotsang.sample.di.DataCreator
import com.haotsang.sample.fragment.RuleFragment
import com.haotsang.sample.theme.ThemeFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject




@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    @Inject
    lateinit var mDataCreator: DataCreator

    override fun initialize() {
        super.initialize()

        val list = mutableListOf("Ball", "CID", "PID", "Theme")
        val mAdapter = ListAdapter(list)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            when (position) {
                0 -> switchFragment(RuleFragment())
                1 -> {
                    if (Build.VERSION.SDK_INT < 26) {
                        return@setOnItemClickListener
                    }
//                    val options = ActivityOptions.makeBasic()
//                    options.launchDisplayId = 1
//                    val secondIntent = Intent()
//                    val cn = ComponentName("org.milk.b2","org.milk.b2.ui.activities.MainActivity")
//                    secondIntent.component = cn
//                    secondIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//                    startActivity(secondIntent,options.toBundle())

                    val intent = Intent("haotsang.action")
                    intent.putExtra("displayId", 1)
                    sendBroadcast(intent)
                }
                2 -> {
                    if (Build.VERSION.SDK_INT < 26) {
                        return@setOnItemClickListener
                    }
//                    val options = ActivityOptions.makeBasic()
//                    options.launchDisplayId = 2
//                    val secondIntent = Intent()
//                    val cn = ComponentName("org.milk.b2","org.milk.b2.ui.activities.MainActivity")
//                    secondIntent.component = cn
//                    secondIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//                    startActivity(secondIntent,options.toBundle())

                    val intent = Intent("haotsang.action")
                    intent.putExtra("displayId", 2)
                    sendBroadcast(intent)
                }

                3 -> switchFragment(ThemeFragment())

                else -> {

                    println(mDataCreator.toString())
                }
            }
        }
        mBinding.rvList.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
        }
    }


    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    private class ListAdapter(list: MutableList<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_base_quick_adapter, list) {
        override fun convert(holder: BaseViewHolder, item: String) {
            holder.setText(R.id.text1, item)
        }
    }
}