package com.zxm.utils.core.screen;

import android.content.Context;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.OrientationEventListener;

/**
 * Created by ZhangXinmin on 2018/9/1.
 * Copyright (c) 2018 . All rights reserved.
 * 屏幕方向监听类
 */
public final class OrientationWatchDog {
    private static final String TAG = OrientationWatchDog.class.getSimpleName();

    private Context mContext;
    //系统的屏幕方向改变监听
    private OrientationEventListener mLandOrientationListener;
    //对外的设置的监听
    private OnOrientationListener mOrientationListener;
    //上次屏幕的方向
    private Orientation mLastOrientation = Orientation.Port;

    public OrientationWatchDog(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 开始监听
     */
    public void startWatch() {
        Log.e(TAG, "startWatch");
        if (mLandOrientationListener == null) {
            mLandOrientationListener = new OrientationEventListener(mContext,
                    SensorManager.SENSOR_DELAY_NORMAL) {
                @Override
                public void onOrientationChanged(int orientation) {
                    //这里的|| 和&& 不能弄错！！
                    //根据手机的方向角度计算。在90和180度上下10度的时候认为横屏了。
                    //竖屏类似。
                    boolean isLand = (orientation < 100 && orientation > 80)
                            || (orientation < 280 && orientation > 260);

                    boolean isPort = (orientation < 10 || orientation > 350)
                            || (orientation < 190 && orientation > 170);

                    if (isLand) {
                        if (mOrientationListener != null) {
                            Log.d(TAG, "ToLand");
                            mOrientationListener.changedToLandScape(mLastOrientation == Orientation.Port);
                        }
                        mLastOrientation = Orientation.Land;
                    } else if (isPort) {
                        if (mOrientationListener != null) {
                            Log.d(TAG, "ToPort");
                            mOrientationListener.changedToPortrait(mLastOrientation == Orientation.Land);
                        }
                        mLastOrientation = Orientation.Port;
                    }

                }
            };
        }

        mLandOrientationListener.enable();
    }

    /**
     * 结束监听
     */
    public void stopWatch() {
        Log.e(TAG, "stopWatch");
        if (mLandOrientationListener != null) {
            mLandOrientationListener.disable();
        }
    }

    /**
     * 销毁监听
     */
    public void destroy() {
        Log.e(TAG, "onDestroy");
        stopWatch();
        mLandOrientationListener = null;
    }

    /**
     * 设置屏幕方向变化事件
     *
     * @param l 事件监听
     */
    public void setOnOrientationListener(OnOrientationListener l) {
        mOrientationListener = l;
    }

    /**
     * 屏幕方向
     */
    private enum Orientation {
        /**
         * 竖屏
         */
        Port,
        /**
         * 横屏
         */
        Land
    }

    /**
     * 屏幕方向变化事件
     */
    public interface OnOrientationListener {
        /**
         * 变为Land
         *
         * @param fromPort 是否是从竖屏变过来的
         */
        void changedToLandScape(boolean fromPort);

        /**
         * 变为Port
         *
         * @param fromLand 是否是从横屏变过来的
         */
        void changedToPortrait(boolean fromLand);
    }

}
