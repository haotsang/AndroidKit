package com.haotsang.common.utils;

import android.content.Context;
import android.media.SoundPool;

import com.haotsang.common.R;


public class SoundPoolUtil {
    private static SoundPoolUtil soundPoolUtil;
    private final SoundPool soundPool;

    public static SoundPoolUtil getInstance(Context context) {
        if (soundPoolUtil == null)
            soundPoolUtil = new SoundPoolUtil(context);
        return soundPoolUtil;
    }

    private SoundPoolUtil(Context context) {
        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .build();
        //加载音频文件
        soundPool.load(context, R.raw.beep, 1);
    }

    public void play5Times() {
        soundPool.play(1, 1, 1, 0, 4, 0.7f);
    }

    public void play() {
        soundPool.play(1, 1, 1, 0, 0, 1);
    }
}