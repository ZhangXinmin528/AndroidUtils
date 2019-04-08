package com.example.androidutils.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.example.androidutils.R;
import com.example.androidutils.base.BaseActivity;
import com.zxm.utils.core.DialogUtil;

/**
 * Created by ZhangXinmin on 2019/1/4.
 * Copyright (c) 2018 . All rights reserved.
 * 权限测试
 */
public class PermissionActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_EXTERNAL = 1001;

    private String[] PERMISSION_EXTERNAL = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private Context mContext;

    @Override
    protected Object setLayout() {
        return R.layout.activity_permission;
    }

    @Override
    protected void initParamsAndValues() {

        mContext = this;

    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_sys).setOnClickListener(this);
        findViewById(R.id.btn_util).setOnClickListener(this);
    }

    /**
     * check permission
     */
    private void checkStrogePermission() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, PERMISSION_EXTERNAL, REQUEST_EXTERNAL);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sys:
                checkStrogePermission();
                break;
            case R.id.btn_util:
                checkPermissions();
                break;
        }
    }

    private void checkPermissions() {
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    DialogUtil.showForceDialog(mContext, getString(R.string.all_deny_write_stroge_permission), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
