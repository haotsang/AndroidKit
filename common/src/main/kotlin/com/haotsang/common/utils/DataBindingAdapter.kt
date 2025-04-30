package com.haotsang.common.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.RequestOptions
import com.haotsang.common.imageloader.IImageLoader
import com.haotsang.common.imageloader.ImageLoader
import com.haotsang.common.list.ListItem
import com.haotsang.common.list.RecyclerViewListAdapter
import com.haotsang.common.utils.ext.dp
import com.haotsang.common.utils.ext.reduceDragSensitivity
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class DataBindingAdapter {

    companion object {

        @BindingAdapter("data")
        @JvmStatic
        fun bindRecyclerView(
            recyclerView: RecyclerView,
            list: List<ListItem>?,
        ) {
            val adapter = recyclerView.adapter
            if (adapter is RecyclerViewListAdapter) {
                adapter.submitList(list)
            }
        }

        @BindingAdapter("layoutManager")
        @JvmStatic
        fun bindLayoutManager(
            recyclerView: RecyclerView,
            orientation: Int
        ) {
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context).apply {
                this.orientation = orientation
                generateDefaultLayoutParams()
            }
        }

        @BindingAdapter("imageUrl", "radius", "placeholder", "error", requireAll = false)
        @JvmStatic
        fun imageUri(imageView: ImageView, uri: Any?, radius: Int?, placeholder: Drawable?, error: Drawable?) {
            ImageLoader.loadImage(
                imageView,
                uri,
                placeholder,
                error,
                transformationType = if ((radius ?: -1) <= 0) null else IImageLoader.ImageTransformationType.RoundCorners(radius!!),
            )
        }

        @BindingAdapter("circleImageUrl", "placeholder", "error", requireAll = false)
        @JvmStatic
        fun circleImageUri(imageView: ImageView, uri: Any?, placeholder: Drawable?, error: Drawable?) {
            ImageLoader.loadImage(
                imageView,
                uri,
                placeholder,
                error,
                IImageLoader.ImageTransformationType.CircleCrop,
            )
        }

        @BindingAdapter("blurImageUrl", "placeholder", "error", requireAll = false)
        @JvmStatic
        fun blurImageUri(imageView: ImageView, uri: Any?, placeholder: Drawable?, error: Drawable?) {
            ImageLoader.loadImage(
                imageView,
                uri,
                placeholder,
                error,
                IImageLoader.ImageTransformationType.Blur(imageView.width / 2, imageView.height / 2),
            )
        }

        @BindingAdapter("data")
        @JvmStatic
        fun bindViewPager2(
            viewPager: ViewPager2,
            list: List<ListItem>?,
        ) {
            viewPager.apply {
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                reduceDragSensitivity()

                // 设置页面转换动画
                setPageTransformer { page, position ->
                    page.translationX = -position
                }

                adapter = RecyclerViewListAdapter().apply {
                    submitList(list)
                }
            }

        }

    }
}