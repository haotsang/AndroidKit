package com.haotsang.common.imageloader

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange

interface IImageLoader {
    fun loadImage(
        imageView: ImageView,
        image: Any?,
        placeholder: Drawable? = null,
        error: Drawable? = null,
        transformationType: ImageTransformationType? = null,
        @FloatRange(from = 0.0, to = 1.0) sizeMultiplier: Float? = null,
        overrideWidth: Int? = null,
        overrideHeight: Int? = null,
    )

    sealed class ImageTransformationType {
        data object CircleCrop : ImageTransformationType() // 圆形裁剪，无需参数
        data class RoundCorners(val radius: Int) : ImageTransformationType() // 圆角，传递半径
        data class Blur(val radius: Int, val sampling: Int) : ImageTransformationType() // 模糊，传递半径和采样率
        data object GrayScale : ImageTransformationType() // 灰度，无需参数
    }
}