package com.example.androidutils.fragment

import android.app.Notification
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.example.androidutils.R
import com.example.androidutils.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_phone.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/16 . All rights reserved.
 */
@Function(group = Group.UTILS, funcName = "手机相关", funcIconRes = R.drawable.icon_phone)
class PhoneFragment : BaseFragment() {

    private var mNotificationManager: NotificationManagerCompat? = null

    override fun setLayoutId(): Int {
        return R.layout.fragment_phone
    }

    override fun initParamsAndValues() {
        //1.获取管理器
        mNotificationManager = NotificationManagerCompat.from(mContext!!)
    }

    override fun initViews(rootView: View) {

        //2.创建对象
        val notification = NotificationCompat.Builder(mContext)
                .setContentTitle("测试通知")
                .setContentText("我要测试一下通知~")
                .setTicker("通知来了") //通知栏首次出现通知，有动画
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL) //通知来了的提示，默认组合
                .setSmallIcon(R.mipmap.ic_launcher)
                .build()

        btn_phone_notification.setOnClickListener(View.OnClickListener {
            if (!mNotificationManager!!.areNotificationsEnabled()) {
                //进入设置页面
                val intent = Intent()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    intent.putExtra("android.provider.extra.APP_PACKAGE", mContext!!.packageName)
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //5.0
                    intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    intent.putExtra("app_package", mContext!!.packageName)
                    intent.putExtra("app_uid", mContext!!.applicationInfo.uid)
                } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {  //4.4
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    intent.addCategory(Intent.CATEGORY_DEFAULT)
                    intent.data = Uri.parse("package:" + mContext!!.packageName)
                } else if (Build.VERSION.SDK_INT >= 15) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                    //                                intent.setAction(Settings.ACTION_SETTINGS);
                    intent.data = Uri.fromParts("package", mContext!!.packageName, null)
                }
                startActivity(intent)
            } else {
                mNotificationManager!!.notify(100, notification)
            }
        })
    }
}