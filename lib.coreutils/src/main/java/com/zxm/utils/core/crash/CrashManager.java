package com.zxm.utils.core.crash;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by ZhangXinmin on 2019/8/15.
 * Copyright (c) 2019 . All rights reserved.
 */
public final class CrashManager implements Thread.UncaughtExceptionHandler {

    private static final String TAG = CrashManager.class.getSimpleName();

    private static final String DEFAULT_DIR ;
    private static final String DEFAULT_DIR_NAME;
    private static final boolean IS_OPEN_CACHE;
    private static final long MAX_CACHE_SIZE;

    private Context mContext;

    //CrashConfig
    private CrashConfig mCrashConfig;
    //Crash params
    private CrashConfig.Builder mCrashParams;

    private CrashManager() {
    }

    public static CrashManager newInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Init {@link CrashConfig}.
     *
     * @param config CrashConfig
     */
    public void init(@NonNull CrashConfig config) {
        mCrashConfig = config;
        mCrashParams = config.builder;
        mContext = mCrashParams.context;
    }

    //Method invoked when the given thread terminates
    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }

    private static class Holder {
        private static final CrashManager INSTANCE = new CrashManager();
    }
}
