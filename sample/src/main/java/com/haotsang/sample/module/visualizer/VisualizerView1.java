package com.haotsang.sample.module.visualizer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.audiofx.Visualizer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * A simple class that draws waveform data received from a
 * {@link Visualizer.OnDataCaptureListener#onWaveFormDataCapture }
 */
 public class VisualizerView1 extends View {

    private byte[] mBytes;
    private float[] mPoints;
    private final Rect mRect = new Rect();

    private final Paint mForePaint = new Paint();
    private final int mSpectrumNum = 48;

    private byte type = 3;

    public VisualizerView1(Context context) {
        super(context);
        init();
    }

    public VisualizerView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mBytes = null;

        mForePaint.setStrokeWidth(8f);
        mForePaint.setAntiAlias(true);
        mForePaint.setColor(Color.rgb(0, 128, 255));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent me)
    {
        // 当用户触碰该组件时，切换波形类型
        if(me.getAction() != MotionEvent.ACTION_DOWN)
        {
            return false;
        }
        type ++;
        if(type >= 4)
        {
            type = 0;
        }
        return true;
    }

    public void updateVisualizer(byte[] fft) {
        if (type==3) {
            byte[] model = new byte[fft.length / 2 + 1];  //

            model[0] = (byte) Math.abs(fft[0]);

            for (int i = 2, j = 1; j < mSpectrumNum; ) {
                model[j] = (byte) Math.hypot(fft[i], fft[i + 1]);
//函数用法：java.lang.Math.hypot(double x, double y) 返回sqrt(x2 +y2)
                i += 2;
                j++;
            }
            mBytes = model;
        } else {
            mBytes = fft;
        }
        invalidate();
        //绘制该视图
    }


//    private double[] fft(byte[] fft){
//        for (int i = 2,j=0; j < this.fft.length;i+=2,j++) {
//            this.fft[j] = Math.hypot(fft[i], fft[i + 1]);
//        }
//        return this.fft;
//    }
    private byte[] fftHypot(byte[] wave){
        byte[] fft=new byte[wave.length];
        for (int n = 2; n < wave.length-1;n++)
        {
            //第k个点频率 getSamplingRate() * k /(getCaptureSize()/2)
            //fft[n - 1] = (byte) ((int)Math.hypot(wave[k] == -1 ?0: wave[k], wave[k + 1] == -1 ?0: wave[k + 1]) & 0x7f);
            int k=n-2;
            fft[k]=(byte)Math.hypot(wave[n],wave[n+1]);
            fft[k]=(byte)(fft[k]*fft[k]*0.95d);
            if(fft[k]<0)
                fft[k]=Byte.MAX_VALUE;
        }
        return fft;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBytes == null) {
            return;
        }

//        if (mPoints == null || mPoints.length < mBytes.length * 4) {
//            mPoints = new float[mBytes.length * 4];
//        }

        mRect.set(0, 0, getWidth(), getHeight());
        //表示一个矩阵，由四条边的坐标组成

        switch(type) {
            // -------绘制块状的波形图-------
            case 0:
                for (int i = 0; i < mBytes.length - 1; i++) {
                    float left = getWidth() * i / (mBytes.length - 1);
                    // 根据波形值计算该矩形的高度
                    float top = mRect.height()/2 - (byte) (mBytes[i + 1] + 128)
                            * (mRect.height()/2) / 128;
                    float right = left + 1;
                    float bottom = mRect.height()/2;
                    canvas.drawRect(left, top, right, bottom, mForePaint);
                }
                break;
            // -------绘制柱状的波形图（每隔18个抽样点绘制一个矩形）-------
            case 1:
                for (int i = 0; i < mBytes.length - 1; i += 18) {
                    float left = mRect.width() * i / (mBytes.length - 1);
                    // 根据波形值计算该矩形的高度
                    float top = mRect.height()/2 - (byte) (mBytes[i + 1] + 128)
                            * (mRect.height()/2) / 128;
                    float right = left + 6;
                    float bottom = mRect.height()/2;
                    canvas.drawRect(left, top, right, bottom, mForePaint);
                }
                break;
            // -------绘制曲线波形图-------
            case 2:
                // 如果point数组还未初始化
                if (mPoints == null || mPoints.length < mBytes.length * 4) {
                    mPoints = new float[mBytes.length * 4];
                }
               // mRect.set(0, 0, getWidth(), getHeight());
                for (int i = 0; i < mBytes.length - 1; i++) {
                    // 计算第i个点的x坐标
                    mPoints[i * 4] = mRect.width() * i / (mBytes.length - 1);
                    // 根据bytes[i]的值（波形点的值）计算第i个点的y坐标
                    mPoints[i * 4 + 1] = mRect.height() / 2
                            + ((byte) (mBytes[i] + 128)) * (mRect.height() / 2) / 128;
                    // 计算第i+1个点的x坐标
                    mPoints[i * 4 + 2] = mRect.width() * (i + 1) / (mBytes.length - 1);
                    // 根据bytes[i+1]的值（波形点的值）计算第i+1个点的y坐标
                    mPoints[i * 4 + 3] = mRect.height() / 2
                            + ((byte) (mBytes[i + 1] + 128)) * (mRect.height() / 2) / 128;
                }
                // 绘制波形曲线
                canvas.drawLines(mPoints, mForePaint);
                break;
            case 3:
                if (mPoints == null || mPoints.length < mBytes.length * 4) {
                    mPoints = new float[mBytes.length * 4];
                }
                //绘制频谱
                final int baseX = mRect.width() / mSpectrumNum;
                final int height = mRect.height();

                for (int i = 0; i < mSpectrumNum
                        ; i++) {
                    if (mBytes[i] < 0) {
                        mBytes[i] = 127;
                    }

                    final int xi = baseX * i + baseX / 2;

                    mPoints[i * 4] = xi;
                    mPoints[i * 4 + 1] = height;

                    mPoints[i * 4 + 2] = xi;
                    mPoints[i * 4 + 3] = height - mBytes[i];
                    canvas.drawLines(mPoints, mForePaint);}
                break;
            default:break;
        }

    }
}