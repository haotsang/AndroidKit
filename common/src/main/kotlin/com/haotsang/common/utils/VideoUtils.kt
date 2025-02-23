package com.haotsang.common.utils

import android.content.Context
import android.content.res.Configuration
import android.util.Log

object VideoUtils {

    private const val TAG = "VideoUtils"

    // 换算显示view的宽高（按视屏的原比例来显示）  
    private fun convertVideoSize(
        context: Context,
        videoWidth: Int,
        videoHeight: Int,
        parentWidth: Int,
        parentHeight: Int
    ): IntArray? {
        if (videoWidth <= 0 && videoHeight <= 0) {
            return null
        }
        var isLandscape = true
        if (context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            isLandscape = false
        }
        val deviceWidth: Int
        val deviceHeight: Int
        if (isLandscape) {
            deviceWidth = parentWidth.coerceAtLeast(parentHeight)
            deviceHeight = parentWidth.coerceAtMost(parentHeight)
        } else {
            deviceWidth = parentWidth.coerceAtMost(parentHeight)
            deviceHeight = parentWidth.coerceAtLeast(parentHeight)
        }
        val deviceRate = deviceWidth.toFloat() / deviceHeight.toFloat()
        val rate: Float = videoWidth.toFloat() / videoHeight.toFloat()
        val width: Int
        val height: Int
        if (rate < deviceRate) {
            height = deviceHeight
            width = (deviceHeight * rate).toInt()
        } else {
            width = deviceWidth
            height = (deviceWidth / rate).toInt()
        }

        Log.d(TAG, "DISPLAY_MODE_ORIGINAL: width $width height = $height")
        return intArrayOf(width, height)
    }
}