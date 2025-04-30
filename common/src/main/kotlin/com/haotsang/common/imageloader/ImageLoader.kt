package com.haotsang.common.imageloader

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.FloatRange
import com.haotsang.common.imageloader.glide.GlideImageLoader

object ImageLoader : IImageLoader {
    override fun loadImage(
        imageView: ImageView,
        image: Any?,
        placeholder: Drawable?,
        error: Drawable?,
        transformationType: IImageLoader.ImageTransformationType?,
        @FloatRange(from = 0.0, to = 1.0) sizeMultiplier: Float?,
        overrideWidth: Int?,
        overrideHeight: Int?
    ) {
        GlideImageLoader.loadImage(
            imageView,
            image,
            placeholder,
            error,
            transformationType,
            sizeMultiplier,
            overrideWidth,
            overrideHeight
        )
    }
}