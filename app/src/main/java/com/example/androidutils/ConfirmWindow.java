package com.example.androidutils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;

import com.zxm.utils.core.screen.ScreenUtil;

/**
 * Created by ZhangXinmin on 2018/10/16.
 * Copyright (c) 2018 . All rights reserved.
 */
public class ConfirmWindow extends PopupWindow implements View.OnClickListener, PopupWindow.OnDismissListener {

    private Context mContext;
    private Activity mActivity;

    public ConfirmWindow(Context context, Activity activity) {
        super(context);

        initParamsAndValues(context, activity);

        initView();
    }

    private void initParamsAndValues(Context context, Activity activity) {
        mContext = context;
        mActivity = activity;
    }

    private void initView() {

        LinearLayout rootLayout = (LinearLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.layout_confirm_window, null);

        setContentView(rootLayout);
        rootLayout.findViewById(R.id.tv_cancel).setOnClickListener(this);
        rootLayout.findViewById(R.id.tv_confirm).setOnClickListener(this);
        setWidth(ScreenUtil.dp2px(mContext, 300));
        setHeight(ScreenUtil.dp2px(mContext, 320));
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(false);
        setBackgroundDrawable(new ColorDrawable(0x00000000));

        setOnDismissListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_confirm:
                dismiss();
                break;
        }
    }

    /**
     * 弹窗
     *
     * @param parent
     */
    public void showWindow(@NonNull View parent) {
        final Window window = mActivity.getWindow();
        final WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = 0.5f;
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setAttributes(layoutParams);

        showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onDismiss() {
        //背景模糊
        final Window window = mActivity.getWindow();
        final WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = 1.0f;
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setAttributes(layoutParams);
    }
}
