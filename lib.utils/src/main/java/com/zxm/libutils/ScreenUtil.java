package com.zxm.libutils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

import static android.Manifest.permission.WRITE_SETTINGS;

/**
 * Created by ZhangXinmin on 2018/5/24.
 * Copyright (c) 2018 . All rights reserved.
 * ScreenUtil is used to provide the methods to get screen attribute.
 */
public final class ScreenUtil {

    private ScreenUtil() {
        throw new UnsupportedOperationException("You must not do this!");
    }

    /**
     * dp to px
     */
    public static int dp2px(@NonNull Context context, @FloatRange(from = 0.0) float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px to dp
     */
    public static int px2dp(@NonNull Context context, @FloatRange(from = 0.0) float pxValue) {
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
    public static int sp2px(@NonNull Context context, @FloatRange(from = 0.0) final float spValue) {
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
    public static int px2sp(@NonNull Context context, @FloatRange(from = 0.0) final float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * Return the width of screen, in pixel.
     *
     * @param context
     * @return the width of screen, in pixel
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return context.getResources().getDisplayMetrics().widthPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * Return the height of screen, in pixel.
     *
     * @param context
     * @return the height of screen, in pixel
     */
    @SuppressLint("ObsoleteSdkInt")
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return context.getResources().getDisplayMetrics().heightPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * Return the width of physical screen
     *
     * @param context
     * @return
     */
    public static double getPhysicsScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        double width = 0;
        if (manager != null) {
            Point point = new Point();
            manager.getDefaultDisplay().getRealSize(point);
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            width = Math.pow(point.x / dm.xdpi, 2);//dm.xdpi是屏幕x方向的真实密度值
        }
        return Math.sqrt(width);
    }

    /**
     * Return the height of physical screen
     *
     * @param context
     * @return
     */
    public static double getPhysicsScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        double height = 0;
        if (manager != null) {
            Point point = new Point();
            manager.getDefaultDisplay().getRealSize(point);
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            height = Math.pow(point.y / dm.ydpi, 2);//dm.ydpi是屏幕y方向的真实密度值
        }
//        return Math.sqrt(height) * 2.54;
        return Math.sqrt(height);
    }


    /**
     * Return the physical screen size.
     * 得到屏幕的物理尺寸，由于该尺寸是在出厂时，厂商写死的，所以仅供参考
     * 计算方法：获取到屏幕的分辨率:point.x和point.y，再取出屏幕的DPI（每英寸的像素数量），
     * 计算长和宽有多少英寸，即：point.x / dm.xdpi，point.y / dm.ydpi，屏幕的长和宽算出来了，
     * 再用勾股定理，计算出斜角边的长度，即屏幕尺寸。
     *
     * @param context
     * @return 英寸
     */
    public static double getPhysicsScreenSize(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        double screenInches = 0;
        if (manager != null) {
            Point point = new Point();
            manager.getDefaultDisplay().getRealSize(point);
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            double x = Math.pow(point.x / dm.xdpi, 2);//dm.xdpi是屏幕x方向的真实密度值，比上面的densityDpi真实。
            double y = Math.pow(point.y / dm.ydpi, 2);//dm.xdpi是屏幕y方向的真实密度值，比上面的densityDpi真实。
            screenInches = Math.sqrt(x + y);
        }
        return screenInches;
    }

    /**
     * Return the density of screen.
     *
     * @return the density of screen
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * Return the screen density expressed as dots-per-inch.
     *
     * @return the screen density expressed as dots-per-inch
     */
    public static int getScreenDensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * Set full screen.
     *
     * @param activity The activity.
     */
    public static void setFullScreen(@NonNull final Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * Set the screen to landscape.
     *
     * @param activity The activity.
     */
    public static void setLandscape(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * Set the screen to portrait.
     *
     * @param activity The activity.
     */
    public static void setPortrait(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * Return whether screen is landscape.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * Return whether screen is portrait.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * Return the rotation of screen.
     *
     * @param activity The activity.
     * @return the rotation of screen
     */
    public static int getScreenRotation(@NonNull final Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            default:
                return 0;
        }
    }

    /**
     * Return the bitmap of screen.
     *
     * @param activity The activity.
     * @return the bitmap of screen
     */
    public static Bitmap screenShot(@NonNull final Activity activity) {
        return screenShot(activity, false);
    }

    /**
     * Return the bitmap of screen.
     *
     * @param activity          The activity.
     * @param isDeleteStatusBar True to delete status bar, false otherwise.
     * @return the bitmap of screen
     */
    public static Bitmap screenShot(@NonNull final Activity activity, boolean isDeleteStatusBar) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap bmp = decorView.getDrawingCache();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret;
        if (isDeleteStatusBar) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = resources.getDimensionPixelSize(resourceId);
            ret = Bitmap.createBitmap(
                    bmp,
                    0,
                    statusBarHeight,
                    dm.widthPixels,
                    dm.heightPixels - statusBarHeight
            );
        } else {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }
        decorView.destroyDrawingCache();
        return ret;
    }

    /**
     * Return whether screen is locked.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isScreenLock(Context context) {
        KeyguardManager km =
                (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return km != null && km.inKeyguardRestrictedInputMode();
    }

    /**
     * Set the duration of sleep.
     * <p>Must hold {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     *
     * @param duration The duration.
     */
    @RequiresPermission(WRITE_SETTINGS)
    public static void setSleepDuration(Context context, final int duration) {
        Settings.System.putInt(
                context.getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT,
                duration
        );
    }

    /**
     * Return the duration of sleep.
     *
     * @return the duration of sleep.
     */
    public static int getSleepDuration(Context context) {
        try {
            return Settings.System.getInt(
                    context.getContentResolver(),
                    Settings.System.SCREEN_OFF_TIMEOUT
            );
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -123;
        }
    }

    /**
     * Return whether device is tablet.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}