package com.haotsang.common.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.PowerManager
import android.provider.Settings
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


class PermissionsUtil(
    private val activity: androidx.activity.ComponentActivity? = null,
    private val fragment: Fragment? = null
): ActivityResultCaller {

    override fun <I : Any?, O : Any?> registerForActivityResult(
        contract: ActivityResultContract<I, O>,
        callback: ActivityResultCallback<O>
    ): ActivityResultLauncher<I> {
        return activity?.registerForActivityResult(contract, callback) ?: fragment?.registerForActivityResult(contract, callback)!!
    }

    override fun <I : Any?, O : Any?> registerForActivityResult(
        contract: ActivityResultContract<I, O>,
        registry: ActivityResultRegistry,
        callback: ActivityResultCallback<O>
    ): ActivityResultLauncher<I> {
        return activity?.registerForActivityResult(contract, registry, callback) ?: fragment?.registerForActivityResult(contract, registry, callback)!!
    }


    // 待申请权限列表
    private var mPermissions: Array<String>? = null
    // 权限已申请回调
    private var mGrantedCallback: (() -> Unit)? = null
    // 权限拒绝回调
    private var mDeniedCallback: (() -> Unit)? = null
    // 权限拒绝且不再询问回调
    private var mRationaleCallback: (() -> Unit)? = null

    // 单个权限申请
    private val mRequestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->


    }

    // 多个权限申请
    private val mRequestMultiplePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { map ->
        //通过的权限
        val grantedList = map.filterValues { it }.mapNotNull { it.key }
        //是否所有权限都通过
        val allGranted = grantedList.size == map.size
        val list = (map - grantedList).map { it.key }


    }



    fun requestPermission(context: Context, vararg permissions: String, granted: (() -> Unit)? = null) {



    }




    fun requestWindowPermission(context: Context) {
        val i = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${context.packageName}"))
        if (i.resolveActivity(context.packageManager) != null) {
            context.startActivity(i)
        } else {

        }
    }







    /**
     * 判断是否拥有权限
     *
     * @param permissions
     * @return
     */
    fun isGranted(context: Context, vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }


    /**
     * 判断某个权限是否被永久拒绝
     *
     * @param permission            请求的权限
     */
    fun isPermissionPermanentDenied(activity: Activity, permission: String): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false
        }

        return !isGranted(activity, permission) && !activity.shouldShowRequestPermissionRationale(permission)
    }


    /**
     * 是否有摄像头权限
     */
    fun isGrantedCameraPermission(context: Context): Boolean {
        return isGranted(context, Manifest.permission.CAMERA)
    }

    /**
     * 是否有录音权限
     */
    fun isGrantedRecordAudioPermission(context: Context): Boolean {
        return isGranted(context, Manifest.permission.RECORD_AUDIO)
    }

    /**
     * 是否有位置权限
     */
    fun isGrantedLocationPermission(context: Context): Boolean {
        return isGranted(context, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    /**
     * 是否有存储权限
     */
    fun isGrantedStoragePermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else isGranted(context, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    /**
     * 是否有安装权限
     */
    fun isGrantedInstallPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.packageManager.canRequestPackageInstalls()
        } else true
    }

    /**
     * 是否有悬浮窗权限
     */
    fun isGrantedWindowPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(context)
        } else true
    }

    /**
     * 是否有系统设置权限
     */
    fun isGrantedSettingPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.System.canWrite(context)
        } else true
    }

    /**
     * 是否有通知栏权限
     */
    fun isGrantedNotifyPermission(context: Context): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    /**
     * 是否有闹钟权限
     */
    fun isGrantedAlarmPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            context.getSystemService(AlarmManager::class.java).canScheduleExactAlarms()
        } else true
    }

    /**
     * 是否有勿扰模式权限
     */
    fun isGrantedNotDisturbPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getSystemService(NotificationManager::class.java).isNotificationPolicyAccessGranted
        } else true
    }

    /**
     * 是否忽略电池优化选项
     */
    fun isGrantedIgnoreBatteryPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getSystemService(PowerManager::class.java)
                .isIgnoringBatteryOptimizations(context.packageName)
        } else true
    }


}
