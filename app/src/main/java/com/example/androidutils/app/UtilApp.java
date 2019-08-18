package com.example.androidutils.app;

import android.app.Application;
import android.os.Environment;

import com.example.androidutils.BuildConfig;
import com.zxm.utils.core.constant.TimeConstants;
import com.zxm.utils.core.crash.CrashConfig;
import com.zxm.utils.core.crash.CrashManager;
import com.zxm.utils.core.file.MemoryConstants;
import com.zxm.utils.core.log.MLogger;

/**
 * Created by ZhangXinmin on 2019/3/19.
 * Copyright (c) 2018 . All rights reserved.
 */
public class UtilApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initLogConfig();

        initCrashCapture();
    }

    private void initCrashCapture() {
        final CrashConfig config =
                new CrashConfig.Builder(getApplicationContext())
                        .crate();

        CrashManager.newInstance().init(config);

    }

    /**
     * init log config
     */
    private void initLogConfig() {
        MLogger.setLogEnable(getApplicationContext(), BuildConfig.LOG_ENABLE);
    }
}
