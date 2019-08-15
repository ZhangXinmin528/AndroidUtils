package com.zxm.utils.core.file;

import android.support.annotation.LongDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ZhangXinmin on 2019/8/15.
 * Copyright (c) 2019 . All rights reserved.
 */
public final class MemoryConstants {

    public static final long B = 1;

    public static final long KB = 1024 * B;

    public static final long MB = 1024 * KB;

    public static final long GB = 1024 * MB;

    @LongDef({B, KB, MB, GB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
