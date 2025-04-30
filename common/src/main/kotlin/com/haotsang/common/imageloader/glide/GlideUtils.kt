package com.haotsang.common.imageloader.glide

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File

object GlideUtils {

    fun loadImage(
        context: Context,
        url: Any?,
        imageView: ImageView,
        options: RequestOptions = RequestOptions().dontAnimate().dontTransform()
    ) {
        Glide
            .with(context)
            .load(url)
            .apply(options)
            .into(imageView)
    }

    fun clearDiskCache(context: Context) {
        Glide.get(context).clearDiskCache()
    }

    fun getCacheDir(context: Context): File? {
        return Glide.getPhotoCacheDir(context)
    }
}