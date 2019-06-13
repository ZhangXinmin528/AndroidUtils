package com.zxm.utils.core.setting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;

import static com.zxm.utils.core.constant.PhoneBrand.BRAND_HUAWEI;
import static com.zxm.utils.core.constant.PhoneBrand.BRAND_MEIZU;
import static com.zxm.utils.core.constant.PhoneBrand.BRAND_ONEPLUS;
import static com.zxm.utils.core.constant.PhoneBrand.BRAND_OPPO;
import static com.zxm.utils.core.constant.PhoneBrand.BRAND_SAMSUNG;
import static com.zxm.utils.core.constant.PhoneBrand.BRAND_VIVO;
import static com.zxm.utils.core.constant.PhoneBrand.BRAND_XIAOMI;

/**
 * Created by ZhangXinmin on 2019/6/11.
 * Copyright (c) 2018 . All rights reserved.
 */
public final class SettingUtils {
    private static final String TAG = SettingUtils.class.getSimpleName();


    private SettingUtils() {
        throw new UnsupportedOperationException("U con't do this!");
    }

    /**
     * 打开自启动管理页面
     *
     * @param context
     */
    @SuppressLint("ObsoleteSdkInt")
    public static void openSelfStartPage(@NonNull Context context) {
        final String brand = Build.BRAND.toUpperCase();
        if (!TextUtils.isEmpty(brand)) {
            final Intent intent = new Intent();
            ComponentName componentName = null;
            switch (brand) {
                case BRAND_HUAWEI:
                    componentName = getHuaweiSelfStartComponent();
                    break;
                case BRAND_SAMSUNG:
                    componentName = getSamsungSelfStartComponent();
                    break;
                case BRAND_XIAOMI:
                    componentName = getXiaomiSelfStartComponent();
                    break;
                case BRAND_VIVO:
                    componentName = getVivoSelfStartComponent();
                    break;
                case BRAND_OPPO:
                    componentName = getOppoSelfStartComponent();
                    break;
                case BRAND_ONEPLUS:
                    //没有自启动管理
                    break;
                case BRAND_MEIZU:
                    componentName = ComponentName.unflattenFromString(
                            "com.meizu.safe/.permission.PermissionMainActivity");
                    break;
                default:
                    break;
            }

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (componentName != null) {
                intent.setComponent(componentName);
            } else {
                intent.setAction(Settings.ACTION_SETTINGS);
            }
            context.startActivity(intent);
        }
    }

    /**
     * 打开蓝牙设置页面
     *
     * @param context
     */
    public static void openBluetoothSetting(@NonNull Context context) {

        if (context == null)
            return;

        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 打开wifi设置
     *
     * @param context
     */
    public static void openWifiSetting(@NonNull Context context) {
        if (context == null)
            return;

        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_WIFI_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 打开NFC设置
     *
     * @param context
     */
    public static void openNfcSetting(@NonNull Context context) {
        if (context == null)
            return;

        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_NFC_SETTINGS);
        context.startActivity(intent);
    }


    /**
     * 打开辅助性功能设置
     *
     * @param context
     */
    public static void openAccessibilitySetting(@NonNull Context context) {
        if (context == null)
            return;

        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 打开应用程序管理
     *
     * @param context
     */
    public static void openApplicationSetting(@NonNull Context context) {
        if (context == null)
            return;

        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 打开未知来源页面
     * <p>一加（9.0）测试通过；
     * <p>OPPO(8.1.0)测试通过；
     *
     * @param context
     * @param requestCode
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @RequiresPermission(Manifest.permission.REQUEST_INSTALL_PACKAGES)
    public static void openUnknownAppSourcesSetting(@NonNull Activity context, int requestCode) {
        if (context == null)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                final boolean hasPermission = packageManager.canRequestPackageInstalls();
                if (!hasPermission) {

                    final Uri uri = Uri.parse("package:" + context.getPackageName());
                    final Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, uri);
                    context.startActivityForResult(intent, requestCode);
                }
            }

        }
    }

    /**
     * 打开开发者模式
     *
     * @param context
     */
    public static void openApplicationDevelopmentSetting(@NonNull Context context) {
        if (context == null)
            return;

        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 获取华为设备自启动管理页面
     * <p>华为Pad(7.0,6.0)测试通过；
     *
     * @return
     */
    private static ComponentName getHuaweiSelfStartComponent() {
        final int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.P) {//9.0
            return new ComponentName("com.huawei.systemmanager",
                    "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
        } else if (sdkInt >= Build.VERSION_CODES.O) {//8.0
            return new ComponentName("com.huawei.systemmanager",
                    "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity");
        } else if (sdkInt >= Build.VERSION_CODES.M) {//7.0,6.0
            return new ComponentName("com.huawei.systemmanager",
                    "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
        } else if (sdkInt >= Build.VERSION_CODES.LOLLIPOP) {//5.0
            return new ComponentName("com.huawei.systemmanager",
                    "com.huawei.systemmanager.optimize.bootstart.BootStartActivity");
        } else {
            return null;
        }
    }

    /**
     * 获取三星自启动管理页
     *
     * @return
     */
    private static ComponentName getSamsungSelfStartComponent() {

        final int sdkInt = Build.VERSION.SDK_INT;

        if (sdkInt >= Build.VERSION_CODES.N_MR1) {//7.1.1
            return new ComponentName("com.samsung.android.sm_cn",
                    "com.samsung.android.sm.ui.ram.AutoRunActivity");
        } else {
            return null;
        }
    }

    /**
     * 获取小米自启动管理页面
     *
     * @return
     */
    private static ComponentName getXiaomiSelfStartComponent() {
        final int sdkInt = Build.VERSION.SDK_INT;

        if (sdkInt >= Build.VERSION_CODES.M) {//6.0,7.0,8.0
            return new ComponentName("com.miui.securitycenter",
                    "com.miui.permcenter.autostart.AutoStartManagementActivity");
        } else {
            return null;
        }
    }

    /**
     * 获取OPPO自启动管理界面
     *
     * <p>OPPO(8.1.0)不能进入自启动管理页面；
     *
     * @return
     */
    private static ComponentName getOppoSelfStartComponent() {
        final int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.O_MR1) {//8.1
            //权限隐私页面
            return new ComponentName("com.coloros.safecenter",
                    "com.coloros.privacypermissionsentry.PermissionTopActivity");
        } else {
            return null;
        }
    }

    /**
     * 获取VIVOvo自启动管理页面
     * <p>VIVO(8.1)测试通过；
     *
     * @return
     */
    private static ComponentName getVivoSelfStartComponent() {
        final int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.O_MR1) {//8.1
            //权限隐私页面
            return new ComponentName("com.vivo.permissionmanager",
                    "com.vivo.permissionmanager.activity.PurviewTabActivity");
        } else {
            return null;
        }
    }
}
