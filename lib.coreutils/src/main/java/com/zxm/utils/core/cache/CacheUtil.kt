package com.zxm.utils.core.cache

import android.content.Context
import android.os.Environment
import com.zxm.utils.core.file.FileUtils
import java.util.*

/**
 * Created by ZhangXinmin on 2020/10/18.
 * Copyright (c) 2020 . All rights reserved.
 * App缓存管理
 */
class CacheUtil private constructor() {
    companion object {

        /**
         * 获取App缓存大小
         */
        fun getAppCacheSize(context: Context): String {
            var length = FileUtils.getDirLength(context.cacheDir)
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                length += FileUtils.getDirLength(context.externalCacheDir)
            }

            return if (length == -1L) "" else byte2FitMemorySize(length)
        }

        /**
         * 清除缓存
         */
        fun clearAppCache(context: Context) {
            FileUtils.deleteAllInDir(context.cacheDir)
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                FileUtils.deleteAllInDir(context.externalCacheDir)
            }
        }

        private fun byte2FitMemorySize(byteNum: Long): String {
            return when {
                byteNum < 0 -> {
                    "shouldn't be less than zero!"
                }
                byteNum < 1024 -> {
                    String.format(Locale.getDefault(), "%.3fB", byteNum.toDouble())
                }
                byteNum < 1048576 -> {
                    String.format(Locale.getDefault(), "%.3fKB", byteNum.toDouble() / 1024)
                }
                byteNum < 1073741824 -> {
                    String.format(Locale.getDefault(), "%.3fMB", byteNum.toDouble() / 1048576)
                }
                else -> {
                    String.format(Locale.getDefault(), "%.3fGB", byteNum.toDouble() / 1073741824)
                }
            }
        }
    }


}