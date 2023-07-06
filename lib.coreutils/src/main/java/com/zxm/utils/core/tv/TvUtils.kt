package com.zxm.utils.core.tv

import android.annotation.SuppressLint
import android.app.Application
import android.app.UiModeManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration

/**
 * Created by zhangxinmin on 2023/7/6.
 */

object TvUtils {

    /**
     * 检测当前应用是否在TV设备上运行
     */
    fun isRunningInTvMode(context: Application): Boolean {
        val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        return uiModeManager.currentModeType == Configuration.UI_MODE_TYPE_TELEVISION
    }

    /**
     * TV设备是否支持电话功能
     */
    fun hasTelephonyHardware(context: Application): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY)
    }

    /**
     * 设备是否支持触摸屏
     */
    fun hasTouchScreenHardware(context: Application): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN)
    }

    /**
     * 设备是否支持相机
     */
    @SuppressLint("UnsupportedChromeOsCameraSystemFeature")
    fun hasCameraHardware(context: Application): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }
}