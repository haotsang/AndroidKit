package com.haotsang.common.view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.RelativeLayout
import java.util.*


class RippleBackground : RelativeLayout {

    private var rippleColor: Int = 0
    private var rippleStrokeWidth: Float = 0.toFloat()
    private var rippleRadius: Float = 0.toFloat()
    private var rippleDurationTime: Int = 0
    private var rRippleDurationTime: Int = 0
    private var rippleAmount: Int = 0
    private var rRippleAmount: Int = 0
    private var rippleDelay: Int = 0
    private var rRippleDelay: Int = 0
    private var rippleScale: Float = 0.toFloat()
    private var rRippleScale: Float = 0.toFloat()
    private var rippleType: Int = 0
    private var paint: Paint? = null
    private var isRippleAnimationRunning = false
    private var animatorSet: AnimatorSet? = null
    private var animatorSet2: AnimatorSet? = null
    private var animatorList: ArrayList<Animator>? = null
    private var animatorList2: ArrayList<Animator>? = null
    private var rippleParams: RelativeLayout.LayoutParams? = null
    private val rippleViewList = ArrayList<RippleView>()
    private val rRippleViewList = ArrayList<RippleView>()



    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (isInEditMode)
            return

        if (null == attrs) {
            throw IllegalArgumentException("Attributes should be provided to this view,")
        }


        rippleColor = Color.parseColor("#80F3BA")

        rippleStrokeWidth = 2F
        rippleRadius = 64F

        rippleDurationTime = DEFAULT_DURATION_TIME
        rippleAmount = DEFAULT_RIPPLE_COUNT
        rippleScale = DEFAULT_SCALE
        rRippleDurationTime = DEFAULT_DURATION_TIME_R
        rRippleAmount = DEFAULT_RIPPLE_COUNT_R
        rRippleScale = DEFAULT_SCALE_R

        rippleType = DEFAULT_FILL_TYPE


        rippleDelay = rippleDurationTime / rippleAmount
        rRippleDelay = rRippleDurationTime / rRippleAmount

        paint = Paint()
        paint!!.isAntiAlias = true
        if (rippleType == DEFAULT_FILL_TYPE) {
            rippleStrokeWidth = 0f
            paint!!.style = Paint.Style.FILL
        } else
            paint!!.style = Paint.Style.STROKE
        paint!!.color = rippleColor

        rippleParams = RelativeLayout.LayoutParams(
            (2 * (rippleRadius + rippleStrokeWidth)).toInt(),
            (2 * (rippleRadius + rippleStrokeWidth)).toInt()
        )
        rippleParams!!.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)

        animatorSet = AnimatorSet()
        animatorSet!!.duration = rippleDurationTime.toLong()
        animatorSet!!.interpolator = AccelerateDecelerateInterpolator()

        animatorSet2 = AnimatorSet()
        animatorSet2!!.duration = rRippleDurationTime.toLong()
        animatorSet2!!.interpolator = ReverseInterpolator()

        animatorList = ArrayList()
        animatorList2 = ArrayList()

        for (i in 0 .. rippleAmount) {
            val rippleView = RippleView(getContext())
            addView(rippleView, rippleParams)
            rippleViewList.add(rippleView)
            val scaleXAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleX", 1.0f, rippleScale)
            scaleXAnimator.repeatCount = 1
            scaleXAnimator.repeatMode = ObjectAnimator.RESTART
            scaleXAnimator.startDelay = (i * rippleDelay).toLong()
            animatorList!!.add(scaleXAnimator)
            val scaleYAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleY", 1.0f, rippleScale)
            scaleYAnimator.repeatCount = 1
            scaleYAnimator.repeatMode = ObjectAnimator.RESTART
            scaleYAnimator.startDelay = (i * rippleDelay).toLong()
            animatorList!!.add(scaleYAnimator)
            val alphaAnimator = ObjectAnimator.ofFloat(rippleView, "Alpha", 1.0f, 0f)
            alphaAnimator.repeatCount = 1
            alphaAnimator.repeatMode = ObjectAnimator.RESTART
            alphaAnimator.startDelay = (i * rippleDelay).toLong()
            animatorList!!.add(alphaAnimator)
        }
        for (i in 0 .. rRippleAmount) {
            val rippleView = RippleView(getContext())
            addView(rippleView, rippleParams)
            rRippleViewList.add(rippleView)
            val scaleXAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleX", 1.0f, rRippleScale)
            scaleXAnimator.repeatCount = 1
            scaleXAnimator.repeatMode = ObjectAnimator.RESTART
            scaleXAnimator.startDelay = (i * rRippleDelay).toLong()
            animatorList2!!.add(scaleXAnimator)
            val scaleYAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleY", 1.0f, rRippleScale)
            scaleYAnimator.repeatCount = 1
            scaleYAnimator.repeatMode = ObjectAnimator.RESTART
            scaleYAnimator.startDelay = (i * rRippleDelay).toLong()
            animatorList2!!.add(scaleYAnimator)
            val alphaAnimator = ObjectAnimator.ofFloat(rippleView, "Alpha", 1.0f, 0f)
            alphaAnimator.repeatCount = 1
            alphaAnimator.repeatMode = ObjectAnimator.RESTART
            alphaAnimator.startDelay = (i * rRippleDelay).toLong()
            animatorList2!!.add(alphaAnimator)
        }

        animatorSet!!.playTogether(animatorList)
        animatorSet2!!.playTogether(animatorList2)
    }

    private inner class RippleView(context: Context) : View(context) {

        init {
            this.visibility = View.INVISIBLE
        }

        override fun onDraw(canvas: Canvas) {
            val radius = Math.min(width, height) / 2
            canvas.drawCircle(radius.toFloat(), radius.toFloat(), radius - rippleStrokeWidth, paint!!)
        }
    }

    fun startRippleAnimation() {
        for (rippleView in rippleViewList) {
            rippleView.visibility = View.VISIBLE
        }
        animatorSet!!.start()
        isRippleAnimationRunning = true

    }

    companion object {

        private const val DEFAULT_RIPPLE_COUNT = 4
        private const val DEFAULT_DURATION_TIME = 2200
        private const val DEFAULT_SCALE = 5f


        private const val DEFAULT_RIPPLE_COUNT_R = 3
        private const val DEFAULT_DURATION_TIME_R = 2200
        private const val DEFAULT_SCALE_R = 5.25f

        private const val DEFAULT_FILL_TYPE = 0
    }


    fun reverseRippleAnimation() {
        for (rippleView in rRippleViewList) {
            rippleView.visibility = View.VISIBLE
        }
        animatorSet2!!.start()
        isRippleAnimationRunning = true


    }



    inner class ReverseInterpolator : Interpolator {
        override fun getInterpolation(paramFloat: Float): Float {
            return Math.abs(paramFloat - 1f)
        }
    }



}