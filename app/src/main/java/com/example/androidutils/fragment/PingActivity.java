package com.example.androidutils.fragment;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidutils.R;
import com.zxm.utils.core.log.MLogger;
import com.zxm.utils.core.net.PingUtil;

/**
 * Created by ZhangXinmin on 2019/5/17.
 * Copyright (c) 2018 . All rights reserved.
 */
public class PingActivity extends BaseActivity {

    private static final String TAG = "PingActivity";

    private EditText mInputEt;
    private RecyclerView mRecyclerView;

    @Override
    protected Object setLayout() {
        return R.layout.activity_ping;
    }

    @Override
    protected void initParamsAndValues() {
        initActionBar();
    }

    @Override
    protected void initViews() {

        mInputEt = findViewById(R.id.et_input_ping);
        mRecyclerView = findViewById(R.id.rv_ping);

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
            final PingUtil.PingResult pingResult = PingUtil.ping(3, 0.5f, ip, false);
            /*if (pingResult != null) {
                if (pingResult.getCode() == 0) {
                    mResultTv.setText(pingResult.successMsg);
                } else {
                    mResultTv.setText(pingResult.successMsg);
                }
            }*/
            MLogger.i(TAG, "result : " + pingResult.toString());
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
