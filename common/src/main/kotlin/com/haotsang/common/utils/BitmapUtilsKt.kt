package com.haotsang.common.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.util.Log

object BitmapUtilsKt {

    fun getBitmapType(bitmapPath: String) {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(bitmapPath, options)
        options.inJustDecodeBounds = false
        val mimeType = options.outMimeType
        Log.d("图片类型", mimeType)
    }

    /**
     * author:  stone
     * email:   aa86799@163.com
     *
     * 根据目标宽度，将bitmap等比缩放。
     * dstWidth 确定的 目标宽度
     */
    fun calculateBitmap(bitmap: Bitmap, dstWidth: Float): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        // 如果得到的宽或者高是0 返回原对象
        if (width == 0 || height == 0) return bitmap
        // h/w = h'/w'  => h' = h/w * w'
        val dstHeight = height * dstWidth / width
        return ThumbnailUtils.extractThumbnail(bitmap, dstWidth.toInt(), dstHeight.toInt())
    }

}