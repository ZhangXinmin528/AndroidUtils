package com.zxm.utils.core.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by ZhangXinmin on 2021/03/05.
 * Copyright (c) 3/5/21 . All rights reserved.
 */
public class AppUtil {
    private AppUtil() {
    }


    /**
     * Return whether application is running.
     *
     * @param context
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppRunning(@NonNull Context context) {
        final ApplicationInfo ai = context.getApplicationInfo();
        final String pkgName = context.getPackageName();
        if (ai != null && !TextUtils.isEmpty(pkgName)) {
            int uid = ai.uid;
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (am != null) {
                List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(Integer.MAX_VALUE);
                if (taskInfo != null && taskInfo.size() > 0) {
                    for (ActivityManager.RunningTaskInfo aInfo : taskInfo) {
                        if (aInfo.baseActivity != null) {
                            if (pkgName.equals(aInfo.baseActivity.getPackageName())) {
                                return true;
                            }
                        }
                    }
                }
                List<ActivityManager.RunningServiceInfo> serviceInfo = am.getRunningServices(Integer.MAX_VALUE);
                if (serviceInfo != null && serviceInfo.size() > 0) {
                    for (ActivityManager.RunningServiceInfo aInfo : serviceInfo) {
                        if (uid == aInfo.uid) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
