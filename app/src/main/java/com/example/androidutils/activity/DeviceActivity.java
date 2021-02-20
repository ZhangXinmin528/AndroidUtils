package com.example.androidutils.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidutils.R;
import com.example.androidutils.base.BaseActivity;
import com.zxm.utils.core.device.DeviceUtil;
import com.zxm.utils.core.permission.PermissionChecker;

import java.util.Arrays;

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

        initActionBar();
    }

    @Override
    protected void initViews() {
        mDetialTv = findViewById(R.id.tv_device_detial);

        final RelativeLayout rebootLayout = findViewById(R.id.layout_reboot);
        if (DeviceUtil.isDeviceRooted()) {
            rebootLayout.setVisibility(View.VISIBLE);
        } else {
            rebootLayout.setVisibility(View.GONE);
        }

        findViewById(R.id.btn_read_info).setOnClickListener(this);
        findViewById(R.id.btn_reboot).setOnClickListener(this);
    }

    @SuppressLint("MissingPermission")
    private String getDeviceDetial() {
        final StringBuilder sb = new StringBuilder();
        sb.append("设备是否Root：").append(DeviceUtil.isDeviceRooted()).append("\n");
        sb.append("\n");

        sb.append("系统版本号：").append(DeviceUtil.getDisplayID()).append("\n");
        sb.append("产品型号：").append(DeviceUtil.getProductName()).append("\n");
        sb.append("产品工业型号：").append(DeviceUtil.getDeviceInfo()).append("\n");
        sb.append("\n");

        sb.append("主板信息：").append(DeviceUtil.getBoardInfo()).append("\n");
        sb.append("设备ABIs：").append(Arrays.asList(DeviceUtil.getABIs())).append("\n");
        sb.append("产品/硬件制造商：").append(DeviceUtil.getManufacturer()).append("\n");
        sb.append("产品/硬件品牌：").append(DeviceUtil.getBrandInfo()).append("\n");
        sb.append("产品最终型号：").append(DeviceUtil.getModel()).append("\n");
        sb.append("系统启动程序版本号：").append(DeviceUtil.getBootLoaderInfo()).append("\n");
        sb.append("基带版本：").append(DeviceUtil.getRadioVersion()).append("\n");
        sb.append("设备序列号: ").append(DeviceUtil.getSerialNumber()).append("\n");
        sb.append("\n");

        sb.append("设备Sdk版本名：").append(DeviceUtil.getSDKVersionName()).append("\n");
        sb.append("设备Sdk版本代码：").append(DeviceUtil.getSDKVersionCode()).append("\n");
        sb.append("Android ID: ").append(DeviceUtil.getAndroidID(mContext)).append("\n");
        sb.append("设备Mac地址：").append(DeviceUtil.getMacAddress(mContext)).append("\n");

        return sb.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_read_info:
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
