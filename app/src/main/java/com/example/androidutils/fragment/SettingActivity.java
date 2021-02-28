package com.example.androidutils.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.androidutils.R;
import com.zxm.utils.core.setting.SettingUtils;

/**
 * Created by ZhangXinmin on 2019/6/11.
 * Copyright (c) 2018 . All rights reserved.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_UNKNOWN_SOURCE = 1001;
    private Context mContext;

    @Override
    protected Object setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initParamsAndValues() {
        mContext = this;
        initActionBar();
    }

    @Override
    protected void initViews() {

        findViewById(R.id.tv_self_start).setOnClickListener(this);
        findViewById(R.id.tv_setting_install_not_market).setOnClickListener(this);

        findViewById(R.id.tv_setting_nfc).setOnClickListener(this);
        findViewById(R.id.tv_setting_wifi).setOnClickListener(this);
        findViewById(R.id.tv_setting_bluetooth).setOnClickListener(this);

        findViewById(R.id.tv_setting_accessibility).setOnClickListener(this);
        findViewById(R.id.tv_setting_application).setOnClickListener(this);
        findViewById(R.id.tv_setting_app_dev).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_self_start:
                SettingUtils.openSelfStartPage(mContext);
                break;
            case R.id.tv_setting_install_not_market:
                installUnknownSourceApp();
                break;
            case R.id.tv_setting_wifi:
                SettingUtils.openWifiSetting(mContext);
                break;
            case R.id.tv_setting_nfc:
                SettingUtils.openNfcSetting(mContext);
                break;
            case R.id.tv_setting_bluetooth:
                SettingUtils.openBluetoothSetting(mContext);
                break;
            case R.id.tv_setting_accessibility:
                SettingUtils.openAccessibilitySetting(mContext);
                break;
            case R.id.tv_setting_application:
                SettingUtils.openApplicationSetting(mContext);
                break;
            case R.id.tv_setting_app_dev:
                SettingUtils.openApplicationDevelopmentSetting(mContext);
                break;
        }
    }

    private void installUnknownSourceApp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final PackageManager packageManager = mContext.getPackageManager();
            if (packageManager != null) {
                final boolean hasPermission = packageManager.canRequestPackageInstalls();
                if (!hasPermission) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("允许安装未知来源应用？")
                            .setMessage(R.string.all_install_unknown_source_app)
                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SettingUtils.openUnknownAppSourcesSetting(SettingActivity.this, REQUEST_UNKNOWN_SOURCE);
                        }
                    }).show();

                }
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_UNKNOWN_SOURCE:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(mContext, "可以安装未知来源应用", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
