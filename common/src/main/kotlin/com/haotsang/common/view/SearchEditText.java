package com.haotsang.common.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * 设计一个 EditText 的文本监听器，停止输入 1s 后，如果文本发生变化则触发监听器
 */
public class SearchEditText extends androidx.appcompat.widget.AppCompatEditText {

    private static final long LIMIT = 1000;

    private OnTextChangedListener mListener;
    private String mStartText = "";// 记录开始输入前的文本内容
    private final Runnable mAction = new Runnable() {
        @Override
        public void run() {
            if (mListener != null) {
                // 判断最终和开始前是否一致
                if (!TextUtils.equals(mStartText, getText().toString())) {
                    mStartText = getText().toString();// 更新 mStartText
                    mListener.onTextChanged(mStartText);
                }
            }
        }
    };

    public SearchEditText(Context context) {
        super(context);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 在 LIMIT 时间内连续输入不触发文本变化
     */
    public void setOnTextChangedListener(OnTextChangedListener listener) {
        mListener = listener;
    }

    @Override
    protected void onTextChanged(final CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        // 移除上一次的回调
        removeCallbacks(mAction);
        postDelayed(mAction, LIMIT);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(mAction);
    }

    public interface OnTextChangedListener {
        void onTextChanged(String text);
    }
}