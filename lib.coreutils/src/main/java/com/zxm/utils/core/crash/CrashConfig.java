package com.zxm.utils.core.crash;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.zxm.utils.core.constant.TimeConstants;
import com.zxm.utils.core.file.MemoryConstants;
import com.zxm.utils.core.phone.PhoneUtil;

import static com.zxm.utils.core.permission.PermissionChecker.checkPersmission;

/**
 * Created by ZhangXinmin on 2019/8/15.
 * Copyright (c) 2019 . All rights reserved.
 *
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
        public String parentDir;
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
         * <p>
         * You'd better use external storage,like {@link Environment#DIRECTORY_DOCUMENTS}.
         *
         * @param parentDir The cache dir
         * @return Builder
         */
        public Builder setParentDir(String parentDir) {
            this.parentDir = parentDir;
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
            if (TextUtils.isEmpty(parentDir)) {
                if (context != null) {
                    if (checkPersmission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            && PhoneUtil.isExternalStorageWritable()) {
                        parentDir = context.getExternalFilesDir(null).getAbsolutePath();
                    } else {
                        parentDir = context.getCacheDir().getPath();
                    }
                }
            }

            if (maxCacheSize == 0) {
                maxCacheSize = 10 * MemoryConstants.MB;
            }

            if (TextUtils.isEmpty(cacheDirName)) {
                cacheDirName = "crash_info";
            }

            return new CrashConfig(this);
        }

    }
}
