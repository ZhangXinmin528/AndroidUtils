package com.example.androidutils.activity;

import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidutils.R;
import com.example.androidutils.base.BaseActivity;
import com.zxm.utils.core.ShellUtils;
import com.zxm.utils.core.log.MLogger;
import com.zxm.utils.core.net.PingUtil;

/**
 * Created by ZhangXinmin on 2019/5/17.
 * Copyright (c) 2018 . All rights reserved.
 */
public class PingActivity extends BaseActivity {

    private static final String TAG = "PingActivity";

    private EditText mInputEt;
    private TextView mResultTv;

    @Override
    protected Object setLayout() {
        return R.layout.activity_ping;
    }

    @Override
    protected void initParamsAndValues() {

    }

    @Override
    protected void initViews() {

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("PingUtil工具使用");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mInputEt = findViewById(R.id.et_input_ping);
        mResultTv = findViewById(R.id.tv_ping_result);

        findViewById(R.id.tv_ping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excuteCommand();
            }
        });
    }

    private void excuteCommand() {
        final String ip = mInputEt.getText().toString().trim();
        if (!TextUtils.isEmpty(ip)) {
            final ShellUtils.CommandResult commandResult = PingUtil.ping(3, 0.5f, ip, false);
            if (commandResult != null) {
                if (commandResult.result == 0) {
                    mResultTv.setText(commandResult.successMsg);
                } else {
                    mResultTv.setText(commandResult.successMsg);
                }
            }
            MLogger.i(TAG, "result : " + commandResult.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ping, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_setting:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
