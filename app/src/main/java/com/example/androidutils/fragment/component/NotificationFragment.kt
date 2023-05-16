package com.example.androidutils.fragment.component

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentNotificationBinding
import com.zxm.utils.core.notifacation.NotificationUtil

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/16 . All rights reserved.
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.Component, funcName = "通知栏", funcIconRes = R.drawable.icon_phone)
class NotificationFragment : BaseFragment(), View.OnClickListener {

    private var mNotificationManager: NotificationManagerCompat? = null

    companion object {
        private const val NOTIFICATION_ID = 1000
        private const val CHANNEL_ID = "1001"
    }

    private lateinit var notificationBinding: FragmentNotificationBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        notificationBinding = FragmentNotificationBinding.inflate(inflater, container, false)
        return notificationBinding.root
    }

    override fun initParamsAndValues() {
        //1.获取管理器
        mNotificationManager = NotificationManagerCompat.from(mContext!!)

        notificationBinding.layoutTitle.tvToolbarTitle.text = "通知栏"
        notificationBinding.layoutTitle.ivToolbarBack.setOnClickListener(this)

        val state = NotificationUtil.isNotificationEnable(mContext!!)
        if (!state) {
            NotificationUtil.goToSettingNotification(mContext!!)
            return
        }

        //2.创建对象
        val notification =
            NotificationCompat.Builder(mContext!!, CHANNEL_ID).setContentTitle("测试通知")
                .setContentText("我要测试一下通知~").setTicker("通知来了") //通知栏首次出现通知，有动画
                .setWhen(System.currentTimeMillis()).setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL) //通知来了的提示，默认组合
                .setSmallIcon(R.mipmap.ic_launcher).build()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, "test_channel", NotificationManager.IMPORTANCE_HIGH)
            mNotificationManager!!.createNotificationChannel(channel)
        }

        notificationBinding.btnPhoneNotification.setOnClickListener(View.OnClickListener {
            mNotificationManager!!.notify(NOTIFICATION_ID, notification)
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        mNotificationManager?.cancel(NOTIFICATION_ID)
        super.onDestroyView()
    }
}