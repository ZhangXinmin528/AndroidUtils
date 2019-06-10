package com.example.androidutils.activity;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidutils.R;
import com.example.androidutils.base.BaseActivity;
import com.zxm.utils.core.keyborad.KeyboradUtil;
import com.zxm.utils.core.text.SpanUtils;

/**
 * Created by ZhangXinmin on 2018/9/1.
 * Copyright (c) 2018 . All rights reserved.
 * User guide for {@link KeyboradUtil}
 */
public class KeyboradActivity extends BaseActivity implements View.OnClickListener,
        KeyboradUtil.OnSoftInputChangedListener {

    private Context mContext;
    private TextView mResutltTv;
    private EditText mInputEt;

    @Override
    protected Object setLayout() {
        return R.layout.activity_keyborad;
    }

    @Override
    protected void initParamsAndValues() {
        mContext = this;
    }

    @Override
    protected void initViews() {
        mResutltTv = findViewById(R.id.tv_result);
        mInputEt = findViewById(R.id.et_input);

        findViewById(R.id.btn_show_keyborad).setOnClickListener(this);
        findViewById(R.id.btn_hide_keyborad).setOnClickListener(this);

        KeyboradUtil.registerSoftInputChangedListener(this, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //显示软键盘
            case R.id.btn_show_keyborad:
                KeyboradUtil.showSoftInput(this);
                break;

            //隐藏软键盘
            case R.id.btn_hide_keyborad:
                KeyboradUtil.hideSoftInput(this);
                break;
        }
    }

    @Override
    public void onSoftInputChanged(int height) {
        mResutltTv.setText(SpanUtils.getBuilder(mContext, "软键盘状态改变：", false)
                .setTextColor(Color.CYAN)
                .append("\n软键盘是否可见：" + KeyboradUtil.isSoftInputVisible(this), true)
                .append("\n软键盘高度：" + height, true)
                .create());
    }

    @Override
    protected void onDestroy() {
        KeyboradUtil.unregisterSoftInputChangedListener(this);
        super.onDestroy();
    }
}
