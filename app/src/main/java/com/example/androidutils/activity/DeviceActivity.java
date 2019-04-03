package com.example.androidutils.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.example.androidutils.R;
import com.example.androidutils.base.BaseActivity;
import com.zxm.libutils.DeviceUtil;

/**
 * Created by ZhangXinmin on 2018/11/23.
 * Copyright (c) 2018 . All rights reserved.
 * 查看设备信息
 */
public class DeviceActivity extends BaseActivity {
    private Context mContext;

    private TextView mDetialTv;

    @Override
    protected Object setLayout() {
        return R.layout.activity_device;
    }

    @Override
    protected void initParamsAndValues() {
        mContext = this;
    }

    @Override
    protected void initViews() {
        mDetialTv = findViewById(R.id.tv_device_detial);
        mDetialTv.setText(getDeviceDetial());
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
}
