package com.example.androidutils.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.androidutils.R;
import com.example.androidutils.base.BaseActivity;

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/16 . All rights reserved.
 */
public class PhoneActivity extends BaseActivity {

    private NotificationManagerCompat mNotificationManager;

    @Override
    protected Object setLayout() {
        return R.layout.activity_phone;
    }

    @Override
    protected void initParamsAndValues() {
        //1.获取管理器
        mNotificationManager = NotificationManagerCompat.from(mContext);

    }

    @Override
    protected void initViews() {

        //2.创建对象
        final Notification notification = new NotificationCompat.Builder(mContext)
                .setContentTitle("测试通知")
                .setContentText("我要测试一下通知~")
                .setTicker("通知来了")//通知栏首次出现通知，有动画
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)//通知来了的提示，默认组合
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        findViewById(R.id.btn_phone_notification)
                .setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ObsoleteSdkInt")
                    @Override
                    public void onClick(View v) {
                        if (!mNotificationManager.areNotificationsEnabled()) {
                            //进入设置页面
                            final Intent intent = new Intent();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                                intent.putExtra("android.provider.extra.APP_PACKAGE", mContext.getPackageName());
                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //5.0
                                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                                intent.putExtra("app_package", mContext.getPackageName());
                                intent.putExtra("app_uid", mContext.getApplicationInfo().uid);
                            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {  //4.4
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + mContext.getPackageName()));
                            } else if (Build.VERSION.SDK_INT >= 15) {
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
//                                intent.setAction(Settings.ACTION_SETTINGS);
                                intent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
                            }
                            startActivity(intent);
                        } else {
                            mNotificationManager.notify(100, notification);
                        }
                    }
                });
    }
}
