package com.zxm.utils.core.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.zxm.utils.core.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangXinmin on 2018/12/17.
 * Copyright (c) 2018 . All rights reserved.
 */
public final class PermissionChecker {
    private PermissionChecker() {
        throw new UnsupportedOperationException("U con't do this!");
    }

    /**
     * Request permissions.
     * 申请一组权限。
     *
     * @param activity    The activity.
     * @param permissions
     * @param requestCode
     */
    public static void requestPermissions(@NonNull Activity activity, @NonNull String[] permissions, int requestCode) {
        if (permissions != null) {
            // 先检查是否已经授权
            if (!checkSeriesPermissions(activity, permissions)) {
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
            }
        }
    }

    /**
     * check permissions
     * 检查一组权限。
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean checkSeriesPermissions(@NonNull Context context, @NonNull String[] permissions) {
        boolean result = false;
        for (String permission : permissions) {
            result = checkPersmission(context, permission);
        }
        return result;
    }

    /**
     * Get denied permissions
     * 获取拒绝的权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public static String[] checkDeniedPermissions(@NonNull Context context, @NonNull String[] permissions) {
        final List<String> deniedList = new ArrayList<>();
        if (permissions != null) {
            for (String permission : permissions) {
                if (!checkPersmission(context, permission)) {
                    deniedList.add(permission);
                }
            }
        }

        if (deniedList.isEmpty()) {
            return null;
        } else {
            return deniedList.toArray(new String[0]);
        }
    }

    /**
     * Check single permission.
     * 检查单一权限.
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean checkPersmission(@NonNull Context context, @NonNull String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public static String matchRequestPermissionRationale(@NonNull Context context, @NonNull String permission) {
        if (!TextUtils.isEmpty(permission)) {
            switch (permission) {
                case Manifest.permission.CAMERA:
                    return context.getResources().getString(R.string.rationale_camera);
                case Manifest.permission.WRITE_CONTACTS:
                case Manifest.permission.READ_CONTACTS:
                    return context.getResources().getString(R.string.rationale_contact);
                case Manifest.permission.ACCESS_FINE_LOCATION:
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                    return context.getResources().getString(R.string.rationale_location);
                case Manifest.permission.RECORD_AUDIO:
                    return context.getResources().getString(R.string.rationale_record_audio);
                case Manifest.permission.READ_PHONE_STATE:
                case Manifest.permission.READ_PHONE_NUMBERS:
                case Manifest.permission.CALL_PHONE:
                case Manifest.permission.ANSWER_PHONE_CALLS:
                case Manifest.permission.ADD_VOICEMAIL:
                case Manifest.permission.USE_SIP:
                    return context.getResources().getString(R.string.rationale_phone);
                case Manifest.permission.READ_SMS:
                case Manifest.permission.RECEIVE_SMS:
                case Manifest.permission.SEND_SMS:
                case Manifest.permission.RECEIVE_WAP_PUSH:
                case Manifest.permission.RECEIVE_MMS:
                    return context.getResources().getString(R.string.rationale_sms);
                case Manifest.permission.READ_EXTERNAL_STORAGE:
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    return context.getResources().getString(R.string.rationale_storage);
                default:
                    return permission;
            }
        }
        return null;
    }

}
