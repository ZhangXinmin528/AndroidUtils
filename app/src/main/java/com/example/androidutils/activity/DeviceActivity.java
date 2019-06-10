package com.example.androidutils.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.androidutils.R;
import com.example.androidutils.base.BaseActivity;
import com.zxm.utils.core.device.DeviceUtil;
import com.zxm.utils.core.permission.PermissionChecker;

/**
 * Created by ZhangXinmin on 2018/11/23.
 * Copyright (c) 2018 . All rights reserved.
 * 查看设备信息
 */
public class DeviceActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;

    private TextView mDetialTv;

    @Override
    protected Object setLayout() {
        return R.layout.activity_device;
    }

    @Override
    protected void initParamsAndValues() {
        mContext = this;

        if (!PermissionChecker.checkPersmission(mContext, Manifest.permission.READ_PHONE_STATE)) {
            PermissionChecker.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1001);
        }
    }

    @Override
    protected void initViews() {
        mDetialTv = findViewById(R.id.tv_device_detial);

        findViewById(R.id.btn_read_info).setOnClickListener(this);
        findViewById(R.id.btn_reboot).setOnClickListener(this);
    }

    @SuppressLint("MissingPermission")
    private String getDeviceDetial() {
        final StringBuilder sb = new StringBuilder();
        sb.append("设备是否Root：").append(DeviceUtil.isDeviceRooted()).append("\n");
        sb.append("Android设备Sdk版本名：").append(DeviceUtil.getSDKVersionName()).append("\n");
        sb.append("Android设备Sdk版本代码：").append(DeviceUtil.getSDKVersionCode()).append("\n");
        sb.append("Android ID: ").append(DeviceUtil.getAndroidID(mContext)).append("\n");
        sb.append("设备序列号: ").append(DeviceUtil.getSerialNumber()).append("\n");
        sb.append("设备Mac地址：").append(DeviceUtil.getMacAddress(mContext)).append("\n");
        sb.append("设备硬件制造商：").append(DeviceUtil.getManufacturer()).append("\n");
        sb.append("设备型号：").append(DeviceUtil.getModel()).append("\n");
        sb.append("设备ABIs：").append(DeviceUtil.getABIs()).append("\n");

        return sb.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_read_info:
                mDetialTv.setVisibility(View.VISIBLE);
                mDetialTv.setText(getDeviceDetial());
                break;
            case R.id.btn_reboot:
                if (DeviceUtil.isDeviceRooted()) {
                    DeviceUtil.reboot(mContext);
                } else {
                    DeviceUtil.reboot(mContext, "");
                }
                break;

        }
    }
}
