package com.example.androidutils.fragment.component

import android.util.Log
import android.view.View
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.example.androidutils.R
import com.example.androidutils.base.BaseFragment
import com.zxm.utils.core.net.NetWatchdog
import com.zxm.utils.core.net.NetWatchdog.NetChangeListener
import com.zxm.utils.core.net.NetWatchdog.NetConnectedListener

/**
 * Created by ZhangXinmin on 2018/11/14.
 * Copyright (c) 2018 . All rights reserved.
 */
@Function(group = Group.Component, funcName = "网络监听", funcIconRes = R.drawable.icon_network_listener)
class NetWatcherFragment : BaseFragment(), NetChangeListener, NetConnectedListener {
    private var mNetWatchdog: NetWatchdog? = null

    override fun setLayoutId(): Int {
        return R.layout.fragment_netwatcher
    }

    override fun initViews(rootView: View) {
        initNetWatchdog()
    }

    private fun initNetWatchdog() {
        mNetWatchdog = NetWatchdog(mContext)
        mNetWatchdog!!.setNetChangeListener(this)
        mNetWatchdog!!.setNetConnectedListener(this)
    }

    override fun initParamsAndValues() {}
    override fun onResume() {
        super.onResume()
        if (mNetWatchdog != null) {
            mNetWatchdog!!.startWatch()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mNetWatchdog != null) {
            mNetWatchdog!!.stopWatch()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mNetWatchdog != null) {
            mNetWatchdog!!.stopWatch()
            mNetWatchdog = null
        }
    }

    //wifi转4G
    override fun onWifiTo4G() {
        Log.e(Companion.TAG, "onWifiTo4G")
    }

    //4G转wifi
    override fun on4GToWifi() {
        Log.e(Companion.TAG, "on4GToWifi")
    }

    //网络断开
    override fun onNetDisconnected() {
        Log.e(Companion.TAG, "onNetDisconnected")
    }

    override fun onReNetConnected(isReconnect: Boolean) {
        Log.e(Companion.TAG, "onReNetConnected:$isReconnect")
    }

    override fun onNetUnConnected() {
        Log.e(Companion.TAG, "onNetUnConnected")
    }

    companion object {
        private val TAG = NetWatcherFragment::class.java.simpleName
    }
}