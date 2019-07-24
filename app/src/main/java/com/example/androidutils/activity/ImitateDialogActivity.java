package com.example.androidutils.activity;

import android.content.Context;
import android.view.MenuItem;
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
        return R.layout.activity_imitate_dialog;
    }

    @Override
    protected void initParamsAndValues() {
        mContext = this;
        initActionBar();
    }

    @Override
    protected void initViews() {
        final TextView message = findViewById(R.id.tv_message);
        message.setText("Activity模拟Dialog");
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_confirm).setOnClickListener(this);
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
