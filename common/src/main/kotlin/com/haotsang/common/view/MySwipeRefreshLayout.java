package com.haotsang.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * 低灵敏度SwipeRefreshLayout
 */
public class MySwipeRefreshLayout extends SwipeRefreshLayout {

    private float mInitialDownY;
    private int mTouchSlop = 300;

    public MySwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mInitialDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float yDiff = ev.getY() - mInitialDownY;
                if (yDiff < mTouchSlop) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * @return 返回灵敏度数值
     */
    public int getTouchSlop() {
        return mTouchSlop;
    }

    /**
     * 设置下拉灵敏度
     *
     * @param mTouchSlop dip值
     */
    public void setTouchSlop(int mTouchSlop) {
        this.mTouchSlop = mTouchSlop;
    }

}