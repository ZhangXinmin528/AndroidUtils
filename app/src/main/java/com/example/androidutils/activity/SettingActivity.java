package com.example.androidutils.activity;

import android.content.Context;
import android.view.View;

import com.example.androidutils.R;
import com.example.androidutils.base.BaseActivity;
import com.zxm.utils.core.setting.SettingUtils;

/**
 * Created by ZhangXinmin on 2019/6/11.
 * Copyright (c) 2018 . All rights reserved.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_self_start:
                SettingUtils.openSelfStartPage(mContext);
                break;
        }
    }
}
