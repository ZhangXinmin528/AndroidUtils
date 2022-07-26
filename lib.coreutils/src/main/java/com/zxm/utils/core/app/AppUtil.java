package com.zxm.utils.core.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

import com.zxm.utils.core.process.ProcessUtils;

/**
 * Created by ZhangXinmin on 2019/8/9.
 * Copyright (c) 2019 . All rights reserved.
 * 应用信息类
 */
public final class AppUtil {

    private static String majorMinorVersion;

    private static int majorVersion = -1;
    private static int minorVersion = -1;
    private static int fixVersion = -1;

    private AppUtil() {
        throw new UnsupportedOperationException("U con't do this!");
    }

    //应用信息P
    //TODO：1.版本号相关

    /**
     * Get App version name
     * versionName字段
     *
     * @param context You'd better application context!
     * @return maybe ""
     */
    public static String getAppVersionName(@NonNull Context context) {
        final PackageManager packageManager = context.getApplicationContext().getPackageManager();
        if (packageManager != null) {
            try {
                final PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                return packageInfo == null ? "" : packageInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 获取 App 的主版本与次版本号。比如说 3.1.2 中的 3.1
     */
    public static String getMajorMinorVersion(@NonNull Context context) {
        if (majorMinorVersion == null || majorMinorVersion.equals("")) {
            majorMinorVersion = getMajorVersion(context) + "." + getMinorVersion(context);
        }
        return majorMinorVersion;
    }

    /**
     * 读取 App 的主版本号，例如 3.1.2，主版本号是 3
     */
    private static int getMajorVersion(@NonNull Context context) {
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
    private static int getMinorVersion(@NonNull Context context) {
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
    public static int getFixVersion(@NonNull Context context) {
        if (fixVersion == -1) {
            String appVersion = getAppVersionName(context);
            String[] parts = appVersion.split("\\.");
            if (parts.length >= 3) {
                fixVersion = Integer.parseInt(parts[2]);
            }
        }
        return fixVersion;
    }

    //TODO:2.VersionCode

    /**
     * Get app version code.
     *
     * @param context You'd better application context!
     * @return maybe 0
     */
    public static int getAppVersionCode(@NonNull Context context) {
        final PackageManager packageManager = context.getApplicationContext().getPackageManager();
        if (packageManager != null) {
            try {
                final PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                return packageInfo == null ? 0 : packageInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return 0;

    }

    //ToDO:3.包名

    /**
     * Get app package name
     *
     * @param context You'd better application context!
     * @return maybe ""
     */
    public static String getAppPackageName(@NonNull Context context) {
        final PackageManager packageManager = context.getApplicationContext().getPackageManager();
        if (packageManager != null) {
            try {
                final PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                return packageInfo == null ? "" : packageInfo.packageName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    //TODO:4.应用图标

    /**
     * Return the application's icon.
     *
     * @param context You'd better application context!.
     * @return the application's icon
     */
    public static Drawable getAppIcon(@NonNull Context context) {

        final PackageManager packageManager = context.getApplicationContext().getPackageManager();
        if (packageManager != null) {
            try {
                final PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                return packageInfo.applicationInfo.loadIcon(packageManager);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;

    }

    //TODO:5.应用名称

    /**
     * Return the application's name.
     *
     * @param context You'd better application context!.
     * @return the application's name
     */
    public static String getAppName(@NonNull Context context) {

        final PackageManager packageManager = context.getApplicationContext().getPackageManager();
        if (packageManager != null) {
            try {
                final PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                return packageInfo.applicationInfo.loadLabel(packageManager).toString();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return "";
            }
        } else {
            return "";
        }

    }

    //TODO:6.应用最小兼容版本

    /**
     * Get app minmum sdk version.
     *
     * @param context You'd better application context!
     * @return maybe 0
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int getMinSdkVersion(@NonNull Context context) {
        final PackageManager packageManager = context.getApplicationContext().getPackageManager();
        if (packageManager != null) {
            try {
                final PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                return packageInfo == null ? 0 : packageInfo.applicationInfo.minSdkVersion;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    //TODO:7.应用目标版本

    /**
     * Get app target sdk version.
     *
     * @param context You'd better application context!
     * @return maybe 0
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int getTargetSdkVersion(@NonNull Context context) {
        final PackageManager packageManager = context.getApplicationContext().getPackageManager();
        if (packageManager != null) {
            try {
                final PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                return packageInfo == null ? 0 : packageInfo.applicationInfo.targetSdkVersion;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        return 0;

    }

    //TODO: 8.App的属性信息

    /**
     * Return whether it is a debug application.
     *
     * @param context Context
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppDebug(@NonNull Context context) {
        final ApplicationInfo info = context.getApplicationInfo();
        return info != null && (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    /**
     * Return whether it is a system application.
     *
     * @param context Context
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppSystem(@NonNull Context context) {
        final ApplicationInfo info = context.getApplicationInfo();
        return info != null && (info.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    /**
     * Return whether application is foreground.
     *
     * @param context Context
     * @return {@code true}: yes<br>{@code false}: no
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @RequiresPermission(android.Manifest.permission.PACKAGE_USAGE_STATS)
    public static boolean isAppForeground(@NonNull Context context) {
        final String packageName = getAppPackageName(context);
        return !TextUtils.isEmpty(packageName) && packageName.equals(ProcessUtils.getForegroundProcessName(context));
    }


}
