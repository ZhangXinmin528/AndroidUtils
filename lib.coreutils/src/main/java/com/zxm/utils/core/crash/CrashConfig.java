package com.zxm.utils.core.crash;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.zxm.utils.core.constant.TimeConstants;
import com.zxm.utils.core.file.MemoryConstants;

/**
 * Created by ZhangXinmin on 2019/8/15.
 * Copyright (c) 2019 . All rights reserved.
 */
public class CrashConfig {

    public Builder builder;

    public CrashConfig(@NonNull Builder builder) {
        this.builder = builder;
    }

    public static class Builder {

        //Application context
        public Context context;
        //缓存父目录
        public String cacheDir;
        //缓存父目录名称
        public String cacheDirName;
        //是否开启缓存
        public boolean isOpenCache;
        //最大缓存大小
        public long maxCacheSize;
        //是否自动清理缓存
        public boolean isAutoClear;
        //缓存最大保存时长
        public long maxSaveDuration;

        /**
         * You'd better use {@link Application#getApplicationContext()} to init the context.
         *
         * @param context Context
         */
        public Builder(@NonNull Context context) {
            this.context = context;
        }

        /**
         * Set the parent cache dir for crash files.
         *
         * @param cacheDir The cache dir
         * @return Builder
         */
        public Builder setCacheDir(String cacheDir) {
            this.cacheDir = cacheDir;
            return this;
        }

        /**
         * Set the parent dir name of crash files.
         *
         * @param cacheDirName The parent dir name
         * @return Builder
         */
        public Builder setCacheDirName(String cacheDirName) {
            this.cacheDirName = cacheDirName;
            return this;
        }

        /**
         * Set wheather to turn on the switch to cache files,when crash occurs.
         *
         * @param openCache wheather or not.
         * @return Builder
         */
        public Builder setOpenCache(boolean openCache) {
            isOpenCache = openCache;
            return this;
        }

        /**
         * Set the maximal length of the crash cache files.
         *
         * @param maxCacheSize
         * @return
         */
        public Builder setMaxCacheSize(@MemoryConstants.Unit long maxCacheSize) {
            this.maxCacheSize = maxCacheSize;
            return this;
        }

        /**
         * Set wheather to clear cache files,when the maximum space or maximum time is reached.
         *
         * @param autoClear true or false
         * @return Builder
         */
        public Builder setAutoClear(boolean autoClear) {
            isAutoClear = autoClear;
            return this;
        }

        /**
         * Set the maximal period of the crash cache files.
         *
         * @param duration
         * @return
         */
        public Builder setMaxSaveDuration(@TimeConstants.Unit long duration) {
            this.maxSaveDuration = duration;
            return this;
        }

        public CrashConfig crate() {
            return new CrashConfig(this);
        }

    }
}
