package com.zxm.libutils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by ZhangXinmin on 2018/5/24.
 * Copyright (c) 2018 . All rights reserved.
 * DensityUtil is used to provide the methods to get screen attribute.
 */
public class DensityUtil {

    private DensityUtil() {
        throw new UnsupportedOperationException("You must not do this!");
    }

    /**
     * get screen width in pixel
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * get screen height in pixel
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * dp to px
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px to dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * Value of sp to value of px.
     *
     * @param context context
     * @param spValue The value of sp.
     * @return value of px
     */
    public static int sp2px(Context context, final float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * Value of px to value of sp.
     *
     * @param context context
     * @param pxValue The value of px.
     * @return value of sp
     */
    public static int px2sp(Context context, final float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

}