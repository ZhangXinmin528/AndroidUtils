package com.example.androidutils.activity;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.androidutils.R;
import com.example.androidutils.base.BaseActivity;

/**
 * Created by ZhangXinmin on 2018/10/16.
 * Copyright (c) 2018 . All rights reserved.
 * 模拟弹窗
 */
public class ImitateDialogActivity extends BaseActivity implements View.OnClickListener {

    private Context mContext;

    @Override
    protected Object setLayout() {
        return R.layout.layout_confirm_window;
    }

    @Override
    protected void initParamsAndValues() {
        mContext = this;
    }

    @Override
    protected void initViews() {
        final TextView message = findViewById(R.id.tv_message);
        message.setText("模拟Dialog");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_confirm:
                finish();
                break;
        }
    }
}
