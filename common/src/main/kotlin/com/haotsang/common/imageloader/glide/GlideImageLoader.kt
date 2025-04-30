package com.haotsang.common.imageloader.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.haotsang.common.imageloader.IImageLoader
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.GrayscaleTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

object GlideImageLoader : IImageLoader {
    override fun loadImage(
        imageView: ImageView,
        image: Any?,
        placeholder: Drawable?,
        error: Drawable?,
        transformationType: IImageLoader.ImageTransformationType?,
        sizeMultiplier: Float?,
        overrideWidth: Int?,
        overrideHeight: Int?
    ) {

        var requestOptions = RequestOptions().dontAnimate().dontTransform()
        placeholder?.let {
            requestOptions = requestOptions.placeholder(it)
        }
        error?.let {
            requestOptions = requestOptions.error(it)
        }
        // 应用变换
        transformationType?.let { type ->
            when (type) {
                is IImageLoader.ImageTransformationType.CircleCrop -> requestOptions.circleCrop()
                is IImageLoader.ImageTransformationType.RoundCorners -> requestOptions.transform(
                    CenterCrop(), RoundedCornersTransformation(
                        type.radius, 0,
                        RoundedCornersTransformation.CornerType.ALL
                    )
                )

                is IImageLoader.ImageTransformationType.Blur -> requestOptions.transform(
                    CenterCrop(), BlurTransformation(type.radius, type.sampling)
                )

                is IImageLoader.ImageTransformationType.GrayScale -> requestOptions.transform(
                    GrayscaleTransformation()
                )
            }
        }
        if (overrideWidth != null && overrideHeight != null) {
            requestOptions = requestOptions.override(
                overrideWidth, overrideHeight
            )
        }

        if (sizeMultiplier != null) {
            requestOptions = requestOptions.sizeMultiplier(
                sizeMultiplier
            )
        }

        GlideUtils.loadImage(
            imageView.context, image, imageView, requestOptions
        )
    }
}