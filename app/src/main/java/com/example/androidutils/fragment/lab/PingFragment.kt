package com.example.androidutils.fragment.lab

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.fastjson.JSONObject
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentPingBinding
import com.zxm.utils.core.log.MLogger
import com.zxm.utils.core.net.PingUtil

/**
 * Created by ZhangXinmin on 2019/5/17.
 * Copyright (c) 2018 . All rights reserved.
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.Lab, funcName = "网络连接", funcIconRes = R.drawable.icon_network_connection)
class PingFragment : BaseFragment(), View.OnClickListener {
    private lateinit var pingBinding: FragmentPingBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        pingBinding = FragmentPingBinding.inflate(inflater, container, false)
        return pingBinding.root
    }

    override fun initParamsAndValues() {
        pingBinding.layoutTitle.tvToolbarTitle.text = "网络连接"
        pingBinding.layoutTitle.ivToolbarBack.setOnClickListener(this)

        pingBinding.tvPing.setOnClickListener(this)
    }

    private fun excuteCommand() {
        val ip: String = pingBinding.etInputPing.text.toString().trim()
        if (!TextUtils.isEmpty(ip)) {
            val pingResult = PingUtil.ping(3, 0.5f, ip, false)
            if (pingResult != null) {
                if (pingResult.getCode() == 0) {
                    pingBinding.tvPingResult.text = JSONObject.toJSONString(pingResult)
                }
            }
            MLogger.i(sTAG, "result : $pingResult")
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }
            R.id.tv_ping -> {
                excuteCommand()
            }
        }
    }
}