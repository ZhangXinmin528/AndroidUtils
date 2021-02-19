package com.zxm.utils.core.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by ZhangXinmin on 2019/8/9.
 * Copyright (c) 2019 . All rights reserved.
 * 软件包工具类
 */
public final class PackageUtil {

    private static String appVersionName;
    private static String majorMinorVersion;
    private static int majorVersion = -1;
    private static int minorVersion = -1;
    private static int fixVersion = -1;

    private PackageUtil() {
        throw new UnsupportedOperationException("U con't do this!");
    }

    /**
     * Get App version name
     * versionName字段
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        if (appVersionName == null) {
            final PackageManager packageManager = context.getApplicationContext().getPackageManager();
            if (packageManager != null) {
                try {
                    final PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                    appVersionName = packageInfo == null ? "" : packageInfo.versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if (appVersionName == null) {
            return "";
        } else {
            return appVersionName;
        }
    }

    /**
     * 获取 App 的主版本与次版本号。比如说 3.1.2 中的 3.1
     */
    public static String getMajorMinorVersion(Context context) {
        if (majorMinorVersion == null || majorMinorVersion.equals("")) {
            majorMinorVersion = getMajorVersion(context) + "." + getMinorVersion(context);
        }
        return majorMinorVersion;
    }

    /**
     * 读取 App 的主版本号，例如 3.1.2，主版本号是 3
     */
    private static int getMajorVersion(Context context) {
        if (majorVersion == -1) {
            String appVersion = getAppVersionName(context);
            String[] parts = appVersion.split("\\.");
            if (parts.length != 0) {
                majorVersion = Integer.parseInt(parts[0]);
            }
        }
        return majorVersion;
    }

    /**
     * 读取 App 的次版本号，例如 3.1.2，次版本号是 1
     */
    private static int getMinorVersion(Context context) {
        if (minorVersion == -1) {
            String appVersion = getAppVersionName(context);
            String[] parts = appVersion.split("\\.");
            if (parts.length >= 2) {
                minorVersion = Integer.parseInt(parts[1]);
            }
        }
        return minorVersion;
    }

    /**
     * 读取 App 的修正版本号，例如 3.1.2，修正版本号是 2
     */
    public static int getFixVersion(Context context) {
        if (fixVersion == -1) {
            String appVersion = getAppVersionName(context);
            String[] parts = appVersion.split("\\.");
            if (parts.length >= 3) {
                fixVersion = Integer.parseInt(parts[2]);
            }
        }
        return fixVersion;
    }
}
