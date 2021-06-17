package com.zxm.utils.core.constant;


import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ZhangXinmin on 2019/6/11.
 * Copyright (c) 2018 . All rights reserved.
 */
public class PhoneBrand {
    public static final String BRAND_SAMSUNG = "SAMSUNG";
    public static final String BRAND_HUAWEI = "HUAWEI";
    public static final String BRAND_XIAOMI = "XIAOMI";
    public static final String BRAND_REDMI = "Readmi";
    public static final String BRAND_VIVO = "VIVO";
    public static final String BRAND_OPPO = "OPPO";
    public static final String BRAND_360 = "360";
    public static final String BRAND_MEIZU = "MEIZU";
    public static final String BRAND_ONEPLUS = "ONEPLUS";

    @StringDef({BRAND_HUAWEI, BRAND_SAMSUNG, BRAND_XIAOMI, BRAND_REDMI,
            BRAND_VIVO, BRAND_OPPO,
            BRAND_ONEPLUS, BRAND_MEIZU, BRAND_360})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Brand {

    }
}
