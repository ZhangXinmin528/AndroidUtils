package com.example.androidutils.activity;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.androidutils.R;
import com.example.androidutils.base.BaseActivity;
import com.zxm.libutils.dialog.CommonDialog;

/**
 * Created by ZhangXinmin on 2018/10/13.
 * Copyright (c) 2018 . All rights reserved.
 * 展示Dialog的使用
 */
public class DialogActivity extends BaseActivity {
    private Context mContext;

    @Override
    protected Object setLayout() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void initParamsAndValues() {
        mContext = this;
    }

    @Override
    protected void initViews() {

        findViewById(R.id.btn_show_dialog)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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
                                }).show(300, 300);

                    }
                });
    }
}
