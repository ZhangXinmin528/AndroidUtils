package com.example.androidutils.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.androidutils.R;
import com.zxm.libutils.NetWatchdog;

/**
 * Created by ZhangXinmin on 2018/11/14.
 * Copyright (c) 2018 . All rights reserved.
 */
public class NetWatcherActivity extends AppCompatActivity implements NetWatchdog.NetChangeListener, NetWatchdog.NetConnectedListener {

    private static final String TAG = NetWatcherActivity.class.getSimpleName();

    private Context mContext;

    private NetWatchdog mNetWatchdog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netwatcher);

        initParamsAndValues();

        initViews();

        initNetWatchdog();
    }

    private void initNetWatchdog() {
        mNetWatchdog = new NetWatchdog(mContext);
        mNetWatchdog.setNetChangeListener(this);
        mNetWatchdog.setNetConnectedListener(this);
    }

    private void initParamsAndValues() {
        mContext = this;

    }

    private void initViews() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNetWatchdog != null) {
            mNetWatchdog.startWatch();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mNetWatchdog != null) {
            mNetWatchdog.stopWatch();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNetWatchdog != null) {
            mNetWatchdog.stopWatch();
            mNetWatchdog = null;
        }
    }

    //wifi转4G
    @Override
    public void onWifiTo4G() {
        Log.e(TAG, "onWifiTo4G");
    }

    //4G转wifi
    @Override
    public void on4GToWifi() {
        Log.e(TAG, "on4GToWifi");
    }

    //网络断开
    @Override
    public void onNetDisconnected() {
        Log.e(TAG, "onNetDisconnected");
    }

    @Override
    public void onReNetConnected(boolean isReconnect) {
        Log.e(TAG, "onReNetConnected:" + isReconnect);
    }

    @Override
    public void onNetUnConnected() {
        Log.e(TAG, "onNetUnConnected");
    }
}