package com.example.androidutils.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.androidutils.ConfirmWindow;
import com.example.androidutils.R;
import com.example.androidutils.base.BaseActivity;
import com.zxm.utils.core.dialog.CommonDialog;

/**
 * Created by ZhangXinmin on 2018/10/13.
 * Copyright (c) 2018 . All rights reserved.
 * 展示Dialog的使用
 */
public class DialogActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private LinearLayoutCompat mRootLayout;
    private ConfirmWindow mConfirmWindow;

    @Override
    protected Object setLayout() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void initParamsAndValues() {
        mContext = this;

        mConfirmWindow = new ConfirmWindow(mContext, this);

        initActionBar();
    }

    @Override
    protected void initViews() {
        mRootLayout = findViewById(R.id.rl_root);

        findViewById(R.id.btn_show_dialog).setOnClickListener(this);
        findViewById(R.id.btn_show_window).setOnClickListener(this);
        findViewById(R.id.btn_show_activity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show_dialog:
                new CommonDialog.Builder(mContext)
                        .setCancelable(true)
                        .setContentView(R.layout.layout_confirm_dialog)
                        .setMessage("这是Dialog演示", R.id.tv_message)
                        .setPositiveButton("确定", R.id.tv_confirm, new CommonDialog.OnClickListener() {
                            @Override
                            public void onClick(CommonDialog dialog) {
                                Toast.makeText(mContext, "点击了确定", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("再想想", R.id.tv_cancel, new CommonDialog.OnClickListener() {
                            @Override
                            public void onClick(CommonDialog dialog) {
                                Toast.makeText(mContext, "点击了再想想", Toast.LENGTH_SHORT).show();
                            }
                        }).display(300, 300);
                break;
            case R.id.btn_show_window:
                mConfirmWindow.showWindow(mRootLayout);
                break;
            case R.id.btn_show_activity:
                Intent intent = new Intent(mContext, ImitateDialogActivity.class);
                startActivity(intent);
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
