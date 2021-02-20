package com.zxm.utils.core.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * Created by ZhangXinmin on 2019/8/9.
 * Copyright (c) 2019 . All rights reserved.
 * 软件包工具类
 */
public final class PackageUtil {

    private static String appVersionName;
    private static String appPackageName;
    private static int appVersionCode;
    private static int minSdkVersion;
    private static int targetSdkVersion;
    private static String majorMinorVersion;

    private static int majorVersion = -1;
    private static int minorVersion = -1;
    private static int fixVersion = -1;

    private PackageUtil() {
        throw new UnsupportedOperationException("U con't do this!");
    }

    //版本号相关

    /**
     * Get App version name
     * versionName字段
     *
     * @param context You'd better application context!
     * @return maybe ""
     */
    public static String getAppVersionName(@NonNull Context context) {
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

    //VersionCode

    /**
     * Get app version code.
     *
     * @param context You'd better application context!
     * @return maybe 0
     */
    public static int getAppVersionCode(@NonNull Context context) {
        if (appVersionCode == 0) {
            final PackageManager packageManager = context.getApplicationContext().getPackageManager();
            if (packageManager != null) {
                try {
                    final PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                    appVersionCode = packageInfo == null ? 0 : packageInfo.versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if (appVersionCode == 0) {
            return 0;
        } else {
            return appVersionCode;
        }
    }

    //包名

    /**
     * Get app package name
     *
     * @param context You'd better application context!
     * @return maybe ""
     */
    public static String getAppPackageName(@NonNull Context context) {
        if (appPackageName == null) {
            final PackageManager packageManager = context.getApplicationContext().getPackageManager();
            if (packageManager != null) {
                try {
                    final PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                    appPackageName = packageInfo == null ? "" : packageInfo.packageName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if (appPackageName == null) {
            return "";
        } else {
            return appPackageName;
        }
    }

    /**
     * Get app minmum sdk version.
     *
     * @param context You'd better application context!
     * @return maybe 0
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int getMinSdkVersion(@NonNull Context context) {
        if (minSdkVersion == 0) {
            final PackageManager packageManager = context.getApplicationContext().getPackageManager();
            if (packageManager != null) {
                try {
                    final PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                    minSdkVersion = packageInfo == null ? 0 : packageInfo.applicationInfo.minSdkVersion;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if (minSdkVersion == 0) {
            return 0;
        } else {
            return minSdkVersion;
        }
    }

    /**
     * Get app minmum sdk version.
     *
     * @param context You'd better application context!
     * @return maybe 0
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int getTargetSdkVersion(@NonNull Context context) {
        if (targetSdkVersion == 0) {
            final PackageManager packageManager = context.getApplicationContext().getPackageManager();
            if (packageManager != null) {
                try {
                    final PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                    targetSdkVersion = packageInfo == null ? 0 : packageInfo.applicationInfo.targetSdkVersion;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if (targetSdkVersion == 0) {
            return 0;
        } else {
            return targetSdkVersion;
        }
    }
}
