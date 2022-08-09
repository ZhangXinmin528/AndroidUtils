package com.example.androidutils.fragment.component

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Toast
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.zxm.utils.core.net.NetWatchdog
import com.zxm.utils.core.net.NetWatchdog.NetChangeListener
import com.zxm.utils.core.net.NetWatchdog.NetConnectedListener
import com.zxm.utils.core.net.NetworkUtil
import com.zxm.utils.core.text.SpanUtils
import kotlinx.android.synthetic.main.fragment_netwatcher.*
import kotlinx.android.synthetic.main.layout_toolbar_back.*

/**
 * Created by ZhangXinmin on 2018/11/14.
 * Copyright (c) 2018 . All rights reserved.
 */
@Function(
    group = Group.UTILS,
    funcName = "网络状态",
    funcIconRes = R.mipmap.icon_network
)
class NetworkFragment : BaseFragment(), NetChangeListener, NetConnectedListener,
    View.OnClickListener {

    private var mNetWatchdog: NetWatchdog? = null

    override fun setLayoutId(): Int {
        return R.layout.fragment_netwatcher
    }

    override fun initParamsAndValues() {}

    override fun initViews(rootView: View) {
        tv_toolbar_title.text = "网络监听"
        iv_toolbar_back.setOnClickListener(this)

        initNetWatchdog()
    }

    private fun initNetWatchdog() {
        mNetWatchdog = NetWatchdog(mContext)
        mNetWatchdog!!.setNetChangeListener(this)
        mNetWatchdog!!.setNetConnectedListener(this)
    }

    private fun getNetworkDetial() {
        val state = NetworkUtil.isConnected(mContext)

        if (state) {
            tv_network_state.text = SpanUtils.getBuilder(mContext!!, "网络状态：", false)
                .setTextColor(Color.GREEN)
                .append("true", true)
                .create()

            tv_network_type.text = SpanUtils.getBuilder(mContext!!, "网络类型：", false)
                .setTextColor(Color.GREEN)
                .append(NetworkUtil.getNetworkType(mContext!!).name, true)
                .create()

            tv_network_ip.text = SpanUtils.getBuilder(mContext!!, "IP地址：", false)
                .setTextColor(Color.GREEN)
                .append(NetworkUtil.getIPAddress(true), true)
                .create()

            tv_network_mobile.text = SpanUtils.getBuilder(mContext!!, "是否移动网络：", false)
                .setTextColor(Color.GREEN)
                .append(NetworkUtil.isMobileData(mContext!!).toString(), true)
                .create()

            tv_network_4g.text = SpanUtils.getBuilder(mContext!!, "是否移动4G：", false)
                .setTextColor(Color.GREEN)
                .append(NetworkUtil.is4G(mContext!!).toString(), true)
                .create()

            tv_network_wifi.text = SpanUtils.getBuilder(mContext!!, "WiFi是否连接：", false)
                .setTextColor(Color.GREEN)
                .append(NetworkUtil.isWifiConnected(mContext!!).toString(), true)
                .create()
        } else {
            tv_network_state.text = SpanUtils.getBuilder(mContext!!, "网络状态：", false)
                .setTextColor(Color.RED)
                .append("false", true)
                .create()

            tv_network_type.text = SpanUtils.getBuilder(mContext!!, "网络类型：", false)
                .setTextColor(Color.RED)
                .append(NetworkUtil.getNetworkType(mContext!!).name, true)
                .create()

            tv_network_ip.text = SpanUtils.getBuilder(mContext!!, "IP地址：", false)
                .setTextColor(Color.RED)
                .append(NetworkUtil.getIPAddress(true), true)
                .create()

            tv_network_mobile.text = SpanUtils.getBuilder(mContext!!, "是否移动网络：", false)
                .setTextColor(Color.RED)
                .append(NetworkUtil.isMobileData(mContext!!).toString(), true)
                .create()

            tv_network_4g.text = SpanUtils.getBuilder(mContext!!, "是否移动4G：", false)
                .setTextColor(Color.RED)
                .append(NetworkUtil.is4G(mContext!!).toString(), true)
                .create()

            tv_network_wifi.text = SpanUtils.getBuilder(mContext!!, "WiFi是否连接：", false)
                .setTextColor(Color.RED)
                .append(NetworkUtil.isWifiConnected(mContext!!).toString(), true)
                .create()
        }
    }

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
        Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show()
        Log.e(sTAG, "onWifiTo4G")
    }

    //4G转wifi
    override fun on4GToWifi() {
        Log.e(sTAG, "on4GToWifi")
    }

    //网络断开
    override fun onNetDisconnected() {
        Log.e(sTAG, "onNetDisconnected")
    }

    override fun onNetReConnected(isReconnect: Boolean) {
        Log.e(sTAG, "onReNetConnected:$isReconnect")
        Toast.makeText(mContext, "onReNetConnected:$isReconnect", Toast.LENGTH_SHORT).show()
        getNetworkDetial()
    }

    override fun onNetUnConnected() {
        Log.e(sTAG, "onNetUnConnected")
        Toast.makeText(mContext, "onNetUnConnected", Toast.LENGTH_SHORT).show()
        getNetworkDetial()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }
        }
    }
}