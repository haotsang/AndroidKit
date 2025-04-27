@file:Suppress("DEPRECATION")
package com.haotsang.common.view

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RSRuntimeException
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import kotlin.math.max
import androidx.core.graphics.createBitmap

class BlurOverlay(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {
    private val mDownsampleFactor: Float = 4f
    private val mOverlayColor: Int = Color.argb(0.5f, 0f, 0f, 0f)
    private val mBlurRadius: Float = 140f

    private val brs: BlurRenderScript by lazy {
        val r = BlurRenderScript()
        val bmp = createBitmap(4, 4)
        r.prepare(context, bmp, 4f)
        r.release()
        bmp.recycle()
        r
    }
    private var mDirty = false
    private var mBitmapToBlur: Bitmap? = null
    private var mBlurredBitmap: Bitmap? = null
    private var mBlurringCanvas: Canvas? = null
    private var mIsRendering = false
    private val mPaint: Paint = Paint()
    private val mRectSrc = Rect()
    private val mRectDst = Rect()

    private var mDecorView: View? = null

    private var mDifferentRoot = false

    private fun releaseBitmap() {
        if (mBitmapToBlur != null) {
            mBitmapToBlur!!.recycle()
            mBitmapToBlur = null
        }
        if (mBlurredBitmap != null) {
            mBlurredBitmap!!.recycle()
            mBlurredBitmap = null
        }
    }

    private fun release() {
        releaseBitmap()
        brs.release()
    }

    private fun prepare(): Boolean {
        var downsampleFactor = mDownsampleFactor
        var radius = mBlurRadius / downsampleFactor
        if (radius > 25) {
            downsampleFactor = downsampleFactor * radius / 25
            radius = 25f
        }

        val width = width
        val height = height

        val scaledWidth = max(1.0, (width / downsampleFactor).toInt().toDouble()).toInt()
        val scaledHeight = max(1.0, (height / downsampleFactor).toInt().toDouble()).toInt()

        var dirty = mDirty

        if (mBlurringCanvas == null || mBlurredBitmap == null || mBlurredBitmap!!.width != scaledWidth || mBlurredBitmap!!.height != scaledHeight) {
            dirty = true
            releaseBitmap()

            var r = false
            try {
                mBitmapToBlur = createBitmap(scaledWidth, scaledHeight)
                mBlurringCanvas = Canvas(mBitmapToBlur!!)

                mBlurredBitmap = createBitmap(scaledWidth, scaledHeight)

                r = true
            } catch (_: OutOfMemoryError) {

            } finally {
                if (!r) { release() }
            }
        }

        if (dirty) {
            if (brs.prepare(context, mBitmapToBlur, radius)) {
                mDirty = false
            } else {
                return false
            }
        }

        return true
    }

    private fun blur(bitmapToBlur: Bitmap?, blurredBitmap: Bitmap?) {
        brs.blur(bitmapToBlur, blurredBitmap)
    }

    private val preDrawListener = ViewTreeObserver.OnPreDrawListener {
        val locations = IntArray(2)
        val oldBmp = mBlurredBitmap
        val decor = mDecorView
        if (decor != null && isShown && prepare()) {
            val redrawBitmap = mBlurredBitmap != oldBmp
            decor.getLocationOnScreen(locations)
            var x = -locations[0]
            var y = -locations[1]

            getLocationOnScreen(locations)
            x += locations[0]
            y += locations[1]

            // just erase transparent
            mBitmapToBlur!!.eraseColor(mOverlayColor and 0xffffff)

            val rc = mBlurringCanvas!!.save()
            mIsRendering = true
            RENDERING_COUNT++
            try {
                mBlurringCanvas!!.scale(
                    1f * mBitmapToBlur!!.width / width,
                    1f * mBitmapToBlur!!.height / height
                )
                mBlurringCanvas!!.translate(-x.toFloat(), -y.toFloat())
                if (decor.background != null) {
                    decor.background.draw(mBlurringCanvas!!)
                }
                decor.draw(mBlurringCanvas!!)
            } catch (_: StopException) {
            } finally {
                mIsRendering = false
                RENDERING_COUNT--
                mBlurringCanvas!!.restoreToCount(rc)
            }

            blur(mBitmapToBlur, mBlurredBitmap)

            if (redrawBitmap || mDifferentRoot) {
                invalidate()
            }
        }
        true
    }

    private val activityDecorView: View?
        get() {
            var ctx = context
            var i = 0
            while (i < 4 && ctx != null && (ctx !is Activity) && ctx is ContextWrapper) {
                ctx = ctx.baseContext
                i++
            }
            return if (ctx is Activity) {
                ctx.window.decorView
            } else {
                null
            }
        }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mDecorView = activityDecorView
        if (mDecorView != null) {
            mDecorView!!.viewTreeObserver.addOnPreDrawListener(preDrawListener)
            mDifferentRoot = mDecorView!!.rootView !== rootView
            if (mDifferentRoot) {
                mDecorView!!.postInvalidate()
            }
        } else {
            mDifferentRoot = false
        }
    }

    override fun onDetachedFromWindow() {
        if (mDecorView != null) {
            mDecorView!!.viewTreeObserver.removeOnPreDrawListener(preDrawListener)
        }
        release()
        super.onDetachedFromWindow()
    }

    override fun draw(canvas: Canvas) {
        if (mIsRendering) {
            // Quit here, don't draw views above me
            throw STOP_EXCEPTION
        } else if (RENDERING_COUNT > 0) {
            // Doesn't support blurview overlap on another blurview
        } else {
            super.draw(canvas)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBlurredBitmap(canvas, mBlurredBitmap, mOverlayColor)
    }

    private fun drawBlurredBitmap(canvas: Canvas, blurredBitmap: Bitmap?, overlayColor: Int) {
        if (blurredBitmap != null) {
            mRectSrc.right = blurredBitmap.width
            mRectSrc.bottom = blurredBitmap.height
            mRectDst.right = width
            mRectDst.bottom = height
            canvas.drawBitmap(blurredBitmap, mRectSrc, mRectDst, null)
        }
        mPaint.color = overlayColor
        canvas.drawRect(mRectDst, mPaint)
    }

    private class StopException : RuntimeException()

    companion object {
        private var RENDERING_COUNT = 0

        private val STOP_EXCEPTION = StopException()
    }
}

class BlurRenderScript {
    private var mRenderScript: RenderScript? = null
    private var mBlurScript: ScriptIntrinsicBlur? = null
    private var mBlurInput: Allocation? = null
    private var mBlurOutput: Allocation? = null

    fun prepare(context: Context?, buffer: Bitmap?, radius: Float): Boolean {
        if (mRenderScript == null) {
            try {
                mRenderScript = RenderScript.create(context)
                mBlurScript = ScriptIntrinsicBlur.create(mRenderScript, Element.U8_4(mRenderScript))
            } catch (e: RSRuntimeException) {
                release()
                return false
            }
        }
        mBlurScript!!.setRadius(radius)

        mBlurInput = Allocation.createFromBitmap(
            mRenderScript, buffer,
            Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT
        ).also {
            mBlurOutput = Allocation.createTyped(mRenderScript, it.getType())
        }

        return true
    }

    fun release() {
        if (mBlurInput != null) {
            mBlurInput!!.destroy()
            mBlurInput = null
        }
        if (mBlurOutput != null) {
            mBlurOutput!!.destroy()
            mBlurOutput = null
        }
        if (mBlurScript != null) {
            mBlurScript!!.destroy()
            mBlurScript = null
        }
        if (mRenderScript != null) {
            mRenderScript!!.destroy()
            mRenderScript = null
        }
    }

    fun blur(input: Bitmap?, output: Bitmap?) {
        mBlurInput!!.copyFrom(input)
        mBlurScript!!.setInput(mBlurInput)
        mBlurScript!!.forEach(mBlurOutput)
        mBlurOutput!!.copyTo(output)
    }
}