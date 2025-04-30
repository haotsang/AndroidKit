package com.haotsang.sample.module.visualizer


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Paint.Cap
import android.graphics.Paint.Join
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.util.Random
import kotlin.math.abs
import kotlin.math.hypot

/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/5 16:03
 * 更 新：2019/7/5 16:03
 * 描 述：音频随音谱率动跳动动画效果
 * ========================================================
 */

class VisualizerView3 @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    companion object {
        private const val DN_W = 480//view宽度与单个音频块占比 - 正常480 需微调
        private const val DN_H = 360//view高度与单个音频块占比
        private const val DN_SL = 15//单个音频块宽度
        private const val DN_SW = 5//单个音频块高度
        private const val MAX_LEVEL = 30//音量柱·音频块 - 最大个数
        private const val CYLINDER_NUM = 26//音量柱 - 最大个数
    }

    private var isHasShadow = false//是否有倒影
    private var shadowNum = 5//倒影个数
    private var shadowColor = Color.GRAY//倒影颜色
    private var visualColor = Color.RED//频块颜色
    private var isGradient = false//音频块颜色是否渐变
    private var colorStart = Color.parseColor("#A47586")
    private var colorCenter = Color.parseColor("#C36084")
    private var colorEnd = Color.parseColor("#F14380")

    private var hgap = 0
    private var vgap = 0
    private var levelStep = 0
    private var strokeWidth = 0f
    private var strokeLength = 0f

    /**
     * It is the paint which is used to draw to visual effect.
     */
    private var mPaint: Paint? = null//画笔

    /**
     * It is the buffer of fft.
     */
    private var mData = ByteArray(CYLINDER_NUM)//音量柱 数组

    private var mDataEn = true

    init {
        isHasShadow = false
        shadowNum =  5
        shadowColor =  Color.GRAY
        visualColor =  Color.RED
        isGradient =  false
        colorStart = Color.parseColor("#A47586")
        colorCenter = Color.parseColor("#C36084")
        colorEnd = Color.parseColor("#F14380")
        init()
    }

    private fun init() {
        mPaint = Paint()//初始化画笔工具
        mPaint!!.isAntiAlias = true//抗锯齿
        mPaint!!.color = -0x29f2db//画笔颜色
        mPaint!!.strokeJoin = Join.ROUND//频块圆角
        mPaint!!.strokeCap = Cap.ROUND//频块圆角

        levelStep = 240 / MAX_LEVEL
    }

    //执行 Layout 操作
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val w: Float = (right - left).toFloat()
        val h: Float = (bottom - top).toFloat()
        val xr: Float = w / DN_W.toFloat()
        val yr: Float = h / DN_H.toFloat()

        strokeWidth = DN_SW * yr
        strokeLength = DN_SL * xr
        hgap = ((w - strokeLength * CYLINDER_NUM) / (CYLINDER_NUM + 1)).toInt()
        vgap = (h / (MAX_LEVEL + 2)).toInt()//频谱块高度
        mPaint!!.strokeWidth = strokeWidth //设置频谱块宽度
    }


    public override fun onDraw(canvas: Canvas) {
        for (i in 0 until CYLINDER_NUM) {
            drawCylinder(canvas, strokeWidth / 2 + hgap.toFloat() + i * (hgap + strokeLength), mData[i])
        }

        //		int j=-4;
        //		for (int i = 0; i < CYLINDER_NUM/2-4; i++) { //绘制25个能量柱
        //
        //			drawCylinder(canvas, strokeWidth / 2 + hgap + i * (hgap + strokeLength), mData[i]);
        //		}
        //		for(int i =CYLINDER_NUM; i>=CYLINDER_NUM/2-4; i--){
        //			j++;
        //			drawCylinder(canvas, strokeWidth / 2 + hgap + (CYLINDER_NUM/2+j-1 )* (hgap + strokeLength), mData[i-1]);
        //		}
    }


    /**
     * 绘制频谱块和倒影
     */
    private fun drawCylinder(canvas: Canvas, x: Float, value: Byte) {
        var value = value
        var y: Float
        if (value <= 0) value = 1
        for (i in 0 until value) {//每个能量柱绘制value个能量块
            if (isHasShadow) {//是否有倒影
                y = (height / 2 - i * vgap - vgap).toFloat()//计算y轴坐标
                val y1 = (height / 2 + i * vgap + vgap).toFloat()
                //绘制音量柱倒影
                if (i <= shadowNum && value > 0) {
                    mPaint!!.color = shadowColor//画笔颜色
                    mPaint!!.alpha = 100 - 100 / shadowNum * i//倒影颜色
                    canvas.drawLine(x, y1, x + strokeLength, y1, mPaint!!)//绘制频谱块
                }
            } else {
                y = (height - i * vgap - vgap).toFloat()
            }
            //绘制频谱块
            mPaint!!.color = visualColor//画笔颜色
            if (isGradient) {
                val backGradient = LinearGradient(x, y, x + strokeLength, y, intArrayOf(colorStart, colorCenter, colorEnd), null, Shader.TileMode.CLAMP)
                mPaint?.shader = backGradient
            }
            canvas.drawLine(x, y, x + strokeLength, y, mPaint!!)//绘制频谱块
        }
    }


    fun updateVisualizer(fft: ByteArray?) {
        fft?:return

        val model = ByteArray(fft.size / 2 + 1)
        if (mDataEn) {
            model[0] = abs(fft[1].toInt()).toByte()
            var j = 1
            var i = 2
            while (i < fft.size) {
                model[j] = hypot(fft[i].toDouble(), fft[i + 1].toDouble()).toInt().toByte()
                i += 2
                j++
            }
        } else {
            for (i in 0 until CYLINDER_NUM) {
                model[i] = 0
            }
        }
        for (i in 0 until CYLINDER_NUM) {
            val a = (abs(model[CYLINDER_NUM - i].toInt()) / levelStep).toByte()

            val b = mData[i]
            if (a > b) {
                mData[i] = a
            } else {
                if (b > 0) {
                    mData[i]--
                }
            }
        }
        postInvalidate()//刷新界面
    }

    private var type = 0
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // 当用户触碰该组件时，切换波形类型
        if (event?.action != MotionEvent.ACTION_DOWN) {
            return false
        }
        type++
        if (type >= 3) {
            type = 0
        }

        when(type) {
            0->{
            setHasShadow(false)
            }
            1->{
                setHasShadow(true)
            }
            2->{
                setGradient(true)
                setHasShadow(true)
            }
            3->{
                setVisualColor(randomColor)
            }
            4->{
//                mVisualizer?.setDataCaptureListener(object : Visualizer.OnDataCaptureListener {
//                    override fun onWaveFormDataCapture(visualizer: Visualizer, waveform: ByteArray, samplingRate: Int) {
//                    }
//                    override fun onFftDataCapture(visualizer: Visualizer, fft: ByteArray, samplingRate: Int) {
//                        mVisualizerView2.updateVisualizer(fft)
//                    }
//                }, Visualizer.getMaxCaptureRate() / 2, true, true)
            }
        }
        return true
    }

    /**
     * It enables or disables the data processs.
     * @param en If this value is true it enables the data process..
     */
    fun enableDataProcess(en: Boolean) {
        mDataEn = en
    }

    fun isHasShadow(): Boolean {
        return isHasShadow
    }

    fun setHasShadow(hasShadow: Boolean) {
        this.isHasShadow = hasShadow
        invalidate()
    }

    fun getShadowNum(): Int {
        return shadowNum
    }

    fun setShadowNum(shadowNum: Int) {
        this.shadowNum = shadowNum
        invalidate()
    }

    fun getShadowColor(): Int {
        return shadowColor
    }

    fun setShadowColor(shadowColor: Int) {
        this.shadowColor = shadowColor
        invalidate()
    }

    fun getVisualColor(): Int {
        return visualColor
    }

    fun setVisualColor(visualColor: Int) {
        this.visualColor = visualColor
        invalidate()
    }

    fun isGradient(): Boolean {
        return isGradient
    }

    fun setGradient(gradient: Boolean) {
        isGradient = gradient
        invalidate()
    }

    fun getColorStart(): Int {
        return colorStart
    }

    fun setColorStart(colorStart: Int) {
        this.colorStart = colorStart
        invalidate()
    }

    fun getColorCenter(): Int {
        return colorCenter
    }

    fun setColorCenter(colorCenter: Int) {
        this.colorCenter = colorCenter
        invalidate()
    }

    fun getColorEnd(): Int {
        return colorEnd
    }

    fun setColorEnd(colorEnd: Int) {
        this.colorEnd = colorEnd
        invalidate()
    }

}
val randomColor: Int
    get() = -0x1000000 or Random().nextInt(0x00ffffff)