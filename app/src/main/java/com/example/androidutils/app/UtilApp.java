package com.example.androidutils.app;

import android.app.Application;

import com.zxm.libutils.log.MLogger;

/**
 * Created by ZhangXinmin on 2019/3/19.
 * Copyright (c) 2018 . All rights reserved.
 */
public class UtilApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initLogConfig();
    }

    /**
     * init log config
     */
    private void initLogConfig() {
        final MLogger.ConfigBuilder builder =
                new MLogger.ConfigBuilder(getApplicationContext());
        MLogger.resetLogConfig(builder);
    }
}
