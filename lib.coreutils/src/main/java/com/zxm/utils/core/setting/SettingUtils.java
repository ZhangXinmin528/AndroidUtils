package com.zxm.utils.core.setting;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
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
     * 获取Oppo自启动管理界面
     *
     * <p>Oppo(8.1)不能进入自启动管理页面；
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
     * 获取Vivo自启动管理页面
     * <p>
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
