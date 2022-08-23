package com.zxm.utils.core.permission;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.zxm.utils.core.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    private static final String sTag = "PermissionChecker";
    private static final int OP_SYSTEM_ALERT_WINDOW = 24;

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
            //先获取未被允许的权限
            String[] deniedPermissions = PermissionChecker.checkDeniedPermissions(activity, permissions);
            if (deniedPermissions != null && deniedPermissions.length > 0) {
                ActivityCompat.requestPermissions(activity, deniedPermissions, requestCode);
            }
        }
    }

    /**
     * check permissions
     * 检查一组权限是否存在未被允许的权限
     *
     * @param context
     * @param permissions
     * @return if the permission has not been granted to the given package , return false.
     */
    public static boolean checkSeriesPermissions(@NonNull Context context, @NonNull String[] permissions) {
        for (String permission : permissions) {
            if (!checkPersmission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get denied permissions
     * 获取拒绝的权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public static String[] checkDeniedPermissions(@NonNull Context context,
                                                  @NonNull String[] permissions) {
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

    /**
     * 判断是否有悬浮窗权限
     *
     * @param context
     * @return
     */
    public static boolean canDrawOverlays(Context context) {
        //android 6.0及以上的判断条件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        }
        return checkOp(context, OP_SYSTEM_ALERT_WINDOW);
    }

    private static boolean checkOp(Context context, int op) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            Class clazz = AppOpsManager.class;
            try {
                Method method = clazz.getDeclaredMethod("checkOp", int.class, int.class, String.class);
                return AppOpsManager.MODE_ALLOWED == (int) method.invoke(manager, op, Process.myUid(), context.getPackageName());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static void requestDrawOverlays(Context context) {
        final Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION",
                Uri.parse("package:" + context.getPackageName()));
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Log.e(sTag, "No activity to handle intent");
        }
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
