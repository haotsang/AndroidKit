package com.haotsang.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.haotsang.sample.di.DataCreator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mDataCreator: DataCreator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        switchFragment(FragmentMain())
    }


    fun switchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

}