package com.zxm.utils.core.notifacation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/16 . All rights reserved.
 * 通知工具类
 */
public final class NotificationUtil {

    private NotificationUtil() {
    }

    /**
     * 通知权限是否打开
     *
     * @param context
     * @return
     */
    public static boolean isNotificationEnable(@NonNull Context context) {
        if (context == null) return false;
        return NotificationManagerCompat.from(context).areNotificationsEnabled();
    }

    public static void goToSettingNotification(@NonNull Context context) {
        if (context == null)
            return;

        //进入设置页面
        final Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //5.0
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {  //4.4
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
        } else if (Build.VERSION.SDK_INT >= 15) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
//                                intent.setAction(Settings.ACTION_SETTINGS);
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        }
        context.startActivity(intent);
    }
}
