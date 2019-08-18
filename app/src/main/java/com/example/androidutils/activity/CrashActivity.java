package com.example.androidutils.activity;

import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidutils.R;
import com.example.androidutils.base.BaseActivity;
import com.zxm.utils.core.crash.CrashManager;

/**
 * Created by ZhangXinmin on 2019/8/12.
 * Copyright (c) 2019 . All rights reserved.
 */
public class CrashActivity extends BaseActivity implements View.OnClickListener {

    private SwitchCompat mSwitch;
    private TextView mResultTv;

    @Override
    protected Object setLayout() {
        return R.layout.activity_crash;
    }

    @Override
    protected void initParamsAndValues() {

        initActionBar();
    }

    @Override
    protected void initViews() {
        mSwitch = findViewById(R.id.switch_crash);

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(mContext, "状态 ： " + isChecked, Toast.LENGTH_SHORT).show();
                if (isChecked) {
                    CrashManager.newInstance().startCapture();
                } else {
                    CrashManager.newInstance().stopCapture();
                }
            }
        });

        mResultTv = findViewById(R.id.tv_crash_result);

        findViewById(R.id.btn_imitate_crash).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_imitate_crash:
                imitateCrash();
                break;
        }
    }

    private void imitateCrash() {
        TextView nullTv = null;
        nullTv.setText("");
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
