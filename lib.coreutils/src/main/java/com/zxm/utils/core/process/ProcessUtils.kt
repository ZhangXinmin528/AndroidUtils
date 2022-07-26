package com.zxm.utils.core.process

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import java.util.*

/**
 * Created by ZhangXinmin on 2022/07/26.
 * Copyright (c) 2022/7/26 . All rights reserved.
 */
object ProcessUtils {

    /**
     * Return the foreground process name.
     * <p>Target APIs must hold
     * {@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" />}</p>
     *
     * @return the foreground process name
     */
    @RequiresApi(Build.VERSION_CODES.M)
    @RequiresPermission(android.Manifest.permission.PACKAGE_USAGE_STATS)
    @JvmStatic
    fun getForegroundProcessName(@NonNull context: Context): String? {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val pInfo = am.runningAppProcesses
        if (pInfo != null && pInfo.size > 0) {
            for (aInfo in pInfo) {
                if (aInfo.importance
                    == RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                ) {
                    return aInfo.processName
                }
            }
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            val pm: PackageManager = context.packageManager
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            val list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            Log.i("ProcessUtils", list.toString())
            if (list.size <= 0) {
                Log.i(
                    "ProcessUtils",
                    "getForegroundProcessName: noun of access to usage information."
                )
                return ""
            }
            try { // Access to usage information.
                val info = pm.getApplicationInfo(context.packageName, 0)
                val aom = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
                if (aom.checkOpNoThrow(
                        AppOpsManager.OPSTR_GET_USAGE_STATS,
                        info.uid,
                        info.packageName
                    ) != AppOpsManager.MODE_ALLOWED
                ) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
                if (aom.checkOpNoThrow(
                        AppOpsManager.OPSTR_GET_USAGE_STATS,
                        info.uid,
                        info.packageName
                    ) != AppOpsManager.MODE_ALLOWED
                ) {
                    Log.i(
                        "ProcessUtils",
                        "getForegroundProcessName: refuse to device usage stats."
                    )
                    return ""
                }
                val usageStatsManager = context
                    .getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
                var usageStatsList: List<UsageStats>? = null
                if (usageStatsManager != null) {
                    val endTime = System.currentTimeMillis()
                    val beginTime = endTime - 86400000 * 7
                    usageStatsList = usageStatsManager
                        .queryUsageStats(
                            UsageStatsManager.INTERVAL_BEST,
                            beginTime, endTime
                        )
                }
                if (usageStatsList == null || usageStatsList.isEmpty()) return ""
                var recentStats: UsageStats? = null
                for (usageStats in usageStatsList) {
                    if (recentStats == null
                        || usageStats.lastTimeUsed > recentStats.lastTimeUsed
                    ) {
                        recentStats = usageStats
                    }
                }
                return recentStats?.packageName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
        }
        return ""
    }

    /**
     * Return all background processes.
     *
     * @return all background processes
     */
    fun getAllbackgroundProcesses(@NonNull context: Context): MutableSet<String> {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val info = am.runningAppProcesses
        val set: MutableSet<String> = mutableSetOf()
        if (info != null) {
            for (aInfo in info) {
                Collections.addAll(set, *aInfo.pkgList)
            }
        }
        return set
    }

    /**
     * Kill all background processes.
     * @return background processes were killed
     */
    @RequiresPermission(android.Manifest.permission.KILL_BACKGROUND_PROCESSES)
    fun killAllBackgroundProcesses(@NonNull context: Context): MutableSet<String> {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var info = am.runningAppProcesses
        val set: MutableSet<String> = HashSet()
        if (info == null) return set
        for (aInfo in info) {
            for (pkg in aInfo.pkgList) {
                am.killBackgroundProcesses(pkg)
                set.add(pkg)
            }
        }
        info = am.runningAppProcesses
        for (aInfo in info) {
            for (pkg in aInfo.pkgList) {
                set.remove(pkg)
            }
        }
        return set
    }

    /**
     * Kill background processes.
     *
     * @param context Context
     * @param packageName The name of the package.
     * @return {@code true}: success<br>{@code false}: fail
     */
    @RequiresPermission(android.Manifest.permission.KILL_BACKGROUND_PROCESSES)
    fun killBackgroundProccesses(@NonNull context: Context, @NonNull packageName: String): Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var info = am.runningAppProcesses
        if (info == null || info.size == 0) return true
        for (aInfo in info) {
            if (mutableListOf(*aInfo.pkgList).contains(packageName)) {
                am.killBackgroundProcesses(packageName)
            }
        }
        info = am.runningAppProcesses
        if (info == null || info.size == 0) return true
        for (aInfo in info) {
            if (listOf(*aInfo.pkgList).contains(packageName)) {
                return false
            }
        }
        return true
    }


}