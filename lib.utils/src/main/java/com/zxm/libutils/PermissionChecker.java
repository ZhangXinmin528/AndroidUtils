package com.zxm.libutils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by ZhangXinmin on 2018/12/17.
 * Copyright (c) 2018 . All rights reserved.
 */
public final class PermissionChecker {
    private PermissionChecker() {
        throw new UnsupportedOperationException("U con't do this!");
    }

    /**
     * request permissions
     *
     * @param activity
     * @param permissions
     * @param requestCode
     */
    public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        // 先检查是否已经授权
        if (!checkPermissionsGroup(activity, permissions)) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
    }

    /**
     * check permissions
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean checkPermissionsGroup(Context context, String[] permissions) {
        boolean result = false;
        for (String permission : permissions) {
            result = checkPersmission(context, permission);
            Log.e("PermissionUtils", "result" + result);
        }
        return result;
    }

    /**
     * check single permission
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean checkPersmission(Context context, String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }
}
