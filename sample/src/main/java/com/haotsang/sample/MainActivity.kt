package com.haotsang.sample

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.haotsang.common.base.BaseActivity
import com.haotsang.common.base.BaseBindingAdapter
import com.haotsang.common.base.BaseBindingViewHolder
import com.haotsang.sample.databinding.ActivityMainBinding
import com.haotsang.sample.databinding.ItemBaseQuickAdapterBinding
import com.haotsang.sample.databinding.ItemDatabindBinding
import com.haotsang.sample.di.DataCreator
import com.haotsang.sample.module.component.BlurOverlayFragment
import com.haotsang.sample.module.component.WickedGradientDrawableFragment
import com.haotsang.sample.module.theme.ThemeFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject




@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    @Inject
    lateinit var mDataCreator: DataCreator

    override fun initialize() {
        super.initialize()

        val list = mutableListOf(
            "Ball",
            "CID",
            "PID",
            "Theme",
            "WickedGradientDrawable",
            "BlurOverlay"
        )
        val mAdapter = ListAdapter(this, this)
        mAdapter.setData(list)
        mAdapter.setOnItemClickListener { s, i ->
            when (i) {
//                0 -> switchFragment(RuleFragment())
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
                4 -> switchFragment(WickedGradientDrawableFragment())
                5 -> switchFragment(BlurOverlayFragment())


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
            .replace(android.R.id.content, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
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