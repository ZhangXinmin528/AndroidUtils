package com.example.androidutils.fragment.util

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentNetwatcherBinding
import com.zxm.utils.core.net.NetWatchdog
import com.zxm.utils.core.net.NetWatchdog.NetChangeListener
import com.zxm.utils.core.net.NetWatchdog.NetConnectedListener
import com.zxm.utils.core.net.NetworkUtil
import com.zxm.utils.core.text.SpanUtils

/**
 * Created by ZhangXinmin on 2018/11/14.
 * Copyright (c) 2018 . All rights reserved.
 */
@SuppressLint("NonConstantResourceId")
@Function(
    group = Group.UTILS, funcName = "网络状态", funcIconRes = R.mipmap.icon_network
)
class NetworkFragment : BaseFragment(), NetChangeListener, NetConnectedListener,
    View.OnClickListener {

    private var mNetWatchdog: NetWatchdog? = null

    private lateinit var netwatcherBinding: FragmentNetwatcherBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        netwatcherBinding = FragmentNetwatcherBinding.inflate(inflater, container, false)
        return netwatcherBinding.root
    }

    override fun initParamsAndValues() {
        initViews()
    }

    fun initViews() {
        netwatcherBinding.layoutTitle.tvToolbarTitle.text = "网络监听"
        netwatcherBinding.layoutTitle.ivToolbarBack.setOnClickListener(this)

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
            netwatcherBinding.tvNetworkState.text =
                SpanUtils.getBuilder(mContext!!, "网络状态：", false).setTextColor(Color.GREEN)
                    .append("true", true).create()

            netwatcherBinding.tvNetworkType.text =
                SpanUtils.getBuilder(mContext!!, "网络类型：", false).setTextColor(Color.GREEN)
                    .append(NetworkUtil.getNetworkType(mContext!!).name, true).create()

            netwatcherBinding.tvNetworkIp.text =
                SpanUtils.getBuilder(mContext!!, "IP地址：", false).setTextColor(Color.GREEN)
                    .append(NetworkUtil.getIPAddress(true), true).create()

            netwatcherBinding.tvNetworkMobile.text =
                SpanUtils.getBuilder(mContext!!, "是否移动网络：", false).setTextColor(Color.GREEN)
                    .append(NetworkUtil.isMobileData(mContext!!).toString(), true).create()

            netwatcherBinding.tvNetwork4g.text =
                SpanUtils.getBuilder(mContext!!, "是否移动4G：", false).setTextColor(Color.GREEN)
                    .append(NetworkUtil.is4G(mContext!!).toString(), true).create()

            netwatcherBinding.tvNetworkWifi.text =
                SpanUtils.getBuilder(mContext!!, "WiFi是否连接：", false).setTextColor(Color.GREEN)
                    .append(NetworkUtil.isWifiConnected(mContext!!).toString(), true).create()
        } else {
            netwatcherBinding.tvNetworkState.text =
                SpanUtils.getBuilder(mContext!!, "网络状态：", false).setTextColor(Color.RED)
                    .append("false", true).create()

            netwatcherBinding.tvNetworkType.text =
                SpanUtils.getBuilder(mContext!!, "网络类型：", false).setTextColor(Color.RED)
                    .append(NetworkUtil.getNetworkType(mContext!!).name, true).create()

            netwatcherBinding.tvNetworkIp.text =
                SpanUtils.getBuilder(mContext!!, "IP地址：", false).setTextColor(Color.RED)
                    .append(NetworkUtil.getIPAddress(true), true).create()

            netwatcherBinding.tvNetworkMobile.text =
                SpanUtils.getBuilder(mContext!!, "是否移动网络：", false).setTextColor(Color.RED)
                    .append(NetworkUtil.isMobileData(mContext!!).toString(), true).create()

            netwatcherBinding.tvNetwork4g.text =
                SpanUtils.getBuilder(mContext!!, "是否移动4G：", false).setTextColor(Color.RED)
                    .append(NetworkUtil.is4G(mContext!!).toString(), true).create()

            netwatcherBinding.tvNetworkWifi.text =
                SpanUtils.getBuilder(mContext!!, "WiFi是否连接：", false).setTextColor(Color.RED)
                    .append(NetworkUtil.isWifiConnected(mContext!!).toString(), true).create()
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