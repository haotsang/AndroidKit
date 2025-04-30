package com.haotsang.sample.module.visualizer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.util.Random;

public class VisualizerView4 extends View {

    private Random random = new Random();
    private byte[] mBytes;
    private float[] mPoints;
    private Rect mRect = new Rect();

    private Paint mForePaint = new Paint();
    private int mSpectrumNum = 48;
    private boolean mFirst = true;

    public VisualizerView4(Context context) {
        super(context);
        init();
    }

    private void init() {
        mBytes = null;

        mForePaint.setStrokeWidth(10f);
        mForePaint.setAntiAlias(true);
        int color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        mForePaint.setColor(color);

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while (true) {
//                        if (mPoints != null) {
//                            for (int i = 0; i < mSpectrumNum; i++) {
//                                float height = mPoints[i * 4 + 3] + 2;
//                                if (height < getHeight() - 100) {
//                                    mPoints[i * 4 + 3] = height;
//                                }
//                            }
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    invalidate();
//                                }
//                            });
//                        }
//                        try {
//                            Thread.sleep(10);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//            }).start();
    }


    public void updateVisualizer(byte[] fft) {
        if (mFirst) {
            mFirst = false;
        }
        byte[] model = new byte[fft.length / 2 + 1];
        model[0] = (byte) Math.abs(fft[1]);
        for (int i = 2, j = 1; j < mSpectrumNum; ) {
            model[j] = (byte) Math.hypot(fft[i], fft[i + 1]);
            i += 2;
            j++;
        }
        mBytes = model;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBytes == null) {
            return;
        }

        if (mPoints == null || mPoints.length < mBytes.length * 4) {
            mPoints = new float[mBytes.length * 4];
        }

        mRect.set(0, 0, getWidth(), getHeight());

//            //绘制波形
//            for (int i = 0; i < mBytes.length - 1; i++) {
//                mPoints[i * 4] = mRect.width() * i / (mBytes.length - 1);
//                mPoints[i * 4 + 1] = mRect.height() / 2
//                        + ((byte) (mBytes[i] + 128)) * (mRect.height() / 2) / 128;
//                mPoints[i * 4 + 2] = mRect.width() * (i + 1) / (mBytes.length - 1);
//                mPoints[i * 4 + 3] = mRect.height() / 2
//                        + ((byte) (mBytes[i + 1] + 128)) * (mRect.height() / 2) / 128;

//            }

        //绘制频谱
        final int baseX = mRect.width() / mSpectrumNum;
        final int height = mRect.height();

        for (int i = 0; i < mSpectrumNum; i++) {
            if (mBytes[i] < 0) {
                mBytes[i] = 127;
            }

            final int xi = baseX * i + baseX / 2;

            mPoints[i * 4] = xi;
            mPoints[i * 4 + 1] = height;

            mPoints[i * 4 + 2] = xi;
//                mPoints[i * 4 + 3] = height - (mBytes[i] * 2 > height ? height : mBytes[i] * 2);
//                mPoints[i * 4 + 3] = height - (mBytes[i] > 10 ? mBytes[i] * 2 : 1);
            mPoints[i * 4 + 3] = height - mBytes[i];

        }

        canvas.drawLines(mPoints, mForePaint);
    }
}