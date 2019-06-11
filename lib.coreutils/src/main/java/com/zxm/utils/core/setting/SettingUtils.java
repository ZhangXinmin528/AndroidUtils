package com.zxm.utils.core.setting;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
                    componentName = new ComponentName("com.huawei.systemmanager",
                            "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
                    break;
                case BRAND_SAMSUNG:
                    componentName = new ComponentName("com.samsung.android.sm_cn",
                            "com.samsung.android.sm.ui.ram.AutoRunActivity");
                    break;
                case BRAND_XIAOMI:
                    componentName = new ComponentName("com.miui.securitycenter",
                            "com.miui.permcenter.autostart.AutoStartManagementActivity");
                    break;
                case BRAND_VIVO:
                    componentName = ComponentName.unflattenFromString(
                            "com.iqoo.secure/.safeguard.PurviewTabActivity");
                    break;
                case BRAND_OPPO:
                    componentName = ComponentName.unflattenFromString(
                            "com.coloros.safecenter/.startupapp.StartupAppListActivity");
                    break;
                case BRAND_ONEPLUS:
                    componentName = new ComponentName("com.oneplus.security",
                            "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity");
                    break;
                case BRAND_MEIZU:
                    componentName = ComponentName.unflattenFromString(
                            "com.meizu.safe/.permission.PermissionMainActivity");
                    break;
                default:
                    break;
            }

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (componentName!=null){
                intent.setComponent(componentName);
            }else {
                intent.setAction(Settings.ACTION_SETTINGS);
            }
            context.startActivity(intent);
        }
    }
}
