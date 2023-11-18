package com.haotsang.androidkit.utils.ext

import android.app.Activity
import androidx.core.app.ActivityCompat


/**
 * recreate
 */
fun Activity.recreateCompat() {
    ActivityCompat.recreate(this)
}