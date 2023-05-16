package com.zxm.utils.core.brightness

import android.app.Application
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.view.Window

/**
 * Created by ZhangXinmin on 2023/5/9.
 * Copyright (c) 2023 . All rights reserved.
 * About Brightness
 */
object BrightnessUtils {
    /**
     * Return wheather automatic brightness mode is enable
     * @param application
     * @return {@code true}: yes<br>{@code false}: no
     */
    fun isAutoBrightnessEnable(application: Application): Boolean {
        return try {
            val mode = Settings.System.getInt(
                application.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE
            )
            mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
        } catch (e: SettingNotFoundException) {
            e.printStackTrace()
            false
        }

    }

    /**
     * Enable or disable automatic brightness mode.
     * <p>Must hold {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     * @param enable True to enabled, false otherwise.
     * @param application
     * @return {@code true}: success<br>{@code false}: fail
     */
    fun setAutoBrightnessEnable(application: Application, enable: Boolean): Boolean {
        return Settings.System.putInt(
            application.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            if (enable) Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC else Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        )
    }

    /**
     * Set the brightness for screen.
     * <p>Must hold {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     * @param application
     * @param brightness 0-255
     * @return {@code true}: success<br>{@code false}: fail
     */
    fun setBrightness(
        application: Application, @androidx.annotation.IntRange(from = 0, to = 255) brightness: Int
    ): Boolean {
        val contentResolver = application.contentResolver
        val state =
            Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness)
        contentResolver.notifyChange(Settings.System.getUriFor("screen_brightness"), null)
        return state
    }

    /**
     * 获取屏幕亮度
     *
     * @return 屏幕亮度 0-255
     */
    fun getBrightness(application: Application): Int {
        return try {
            Settings.System.getInt(
                application.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS
            )
        } catch (e: SettingNotFoundException) {
            e.printStackTrace()
            0
        }
    }
    /**
     * Set the brightness for window.
     * @param window
     * @param brightness
     */
    fun setWindowBrightness(
        window: Window, @androidx.annotation.IntRange(from = 0, to = 255) brightness: Int
    ) {
        val layoutParams = window.attributes
        layoutParams.screenBrightness = brightness / 255f
        window.attributes = layoutParams

    }
}