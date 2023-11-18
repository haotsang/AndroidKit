package com.haotsang.androidkit.utils


import android.content.Context
import android.os.Vibrator
import androidx.annotation.RequiresPermission

object VibratorUtil {

    /**
     * 获取Vibrator实例
     */
    @JvmStatic
    val vibrator: Vibrator by lazy {
        AndUtil.appContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    /**
     * 检测设备是否具有振动器
     */
    @JvmStatic
    val hasVibrator
        get() = vibrator.hasVibrator()

    /**
     * 开启振动
     * @param pattern: 设置振动的间歇和持续时间；每一对中的第一个值表示等待的毫秒数，第二个值表示在持续振动的毫秒数。
     * @param repeat : 重复的次数，默认为-1不重复
     */
    @JvmStatic
    @JvmOverloads
    @RequiresPermission(android.Manifest.permission.VIBRATE)
    fun vibrate(vararg pattern: Long, repeat: Int = -1) {
        if (hasVibrator) {
            vibrator.vibrate(pattern, repeat)
        }
    }

    /**
     * 取消振动
     */
    @JvmStatic
    @RequiresPermission(android.Manifest.permission.VIBRATE)
    fun cancel() {
        if (hasVibrator) {
            vibrator.cancel()
        }
    }


}