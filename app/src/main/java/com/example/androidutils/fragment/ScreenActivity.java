package com.example.androidutils.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidutils.R;
import com.zxm.utils.core.screen.ScreenUtil;

/**
 * Created by ZhangXinmin on 2018/8/30.
 * Copyright (c) 2018 . All rights reserved.
 */
public class ScreenActivity extends BaseActivity implements View.OnClickListener {

    private Context mContext;
    private TextView mInfoTv;
    private ImageView mTestIv;

    @Override
    protected Object setLayout() {
        return R.layout.activity_screen;
    }

    @Override
    protected void initParamsAndValues() {
        mContext = this;
        initActionBar();
    }

    @Override
    protected void initViews() {
        mInfoTv = findViewById(R.id.tv_device_info);
        mInfoTv.setText(getString(R.string.all_screen_info, getDeviceInfo()));

        findViewById(R.id.btn_fullscreen).setOnClickListener(this);
        findViewById(R.id.btn_screenShot).setOnClickListener(this);
        mTestIv = findViewById(R.id.iv_test);
    }

    /**
     * 获取设备信息
     *
     * @return
     */
    private String getDeviceInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n屏幕密度：" + ScreenUtil.getScreenDensity(mContext) + "\n");
        sb.append("屏幕DPI：" + ScreenUtil.getScreenDensityDpi(mContext) + "\n");
        sb.append("屏幕宽度：" + ScreenUtil.getScreenWidth(mContext) + "\n");
        sb.append("屏幕高度：" + ScreenUtil.getScreenHeight(mContext) + "\n");
        sb.append("屏幕物理尺寸(英寸)：" + ScreenUtil.getPhysicsScreenSize(mContext) + "\n");
        sb.append("屏幕物理宽度(cm)：" + ScreenUtil.getPhysicsScreenWidth(mContext) + "\n");
        sb.append("屏幕物理高度(cm)：" + ScreenUtil.getPhysicsScreenHeight(mContext) + "\n");
        sb.append("是否横屏：" + ScreenUtil.isLandscape(mContext) + "\n");
        sb.append("屏幕旋转角度：" + ScreenUtil.getScreenRotation(this) + "\n");
        sb.append("是否锁屏：" + ScreenUtil.isScreenLock(mContext) + "\n");
        sb.append("是否平板：" + ScreenUtil.isTablet(mContext) + "\n");
        return sb.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fullscreen:
                ScreenUtil.setFullScreen(ScreenActivity.this);
                break;
            case R.id.btn_screenShot:
                final Bitmap bitmap = ScreenUtil.screenShot(ScreenActivity.this);
                mTestIv.post(new Runnable() {
                    @Override
                    public void run() {
                        mTestIv.setImageBitmap(bitmap);
                    }
                });
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
