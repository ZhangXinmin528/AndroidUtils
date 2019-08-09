package com.zxm.utils.core.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by ZhangXinmin on 2019/8/9.
 * Copyright (c) 2019 . All rights reserved.
 * 软件信息工具类
 */
public final class AppUtil {

    private AppUtil() {
        throw new UnsupportedOperationException("U con't do this!");
    }

    /**
     * Get App version name
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        final PackageManager packageManager = context.getApplicationContext().getPackageManager();
        if (packageManager != null) {
            try {
                final PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                versionName = packageInfo == null ? "" : packageInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return versionName;
    }
}
