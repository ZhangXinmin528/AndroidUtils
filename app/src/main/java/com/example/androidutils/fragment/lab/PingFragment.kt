package com.example.androidutils.fragment.lab

import android.text.TextUtils
import android.view.View
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.zxm.utils.core.log.MLogger
import com.zxm.utils.core.net.PingUtil
import kotlinx.android.synthetic.main.fragment_ping.*
import kotlinx.android.synthetic.main.layout_toolbar_back.*

/**
 * Created by ZhangXinmin on 2019/5/17.
 * Copyright (c) 2018 . All rights reserved.
 */
@Function(group = Group.Lab, funcName = "网络连接", funcIconRes = R.drawable.icon_network_connection)
class PingFragment : BaseFragment(), View.OnClickListener {
    override fun setLayoutId(): Int {
        return R.layout.fragment_ping
    }

    override fun initParamsAndValues() {}

    override fun initViews(rootView: View) {
        tv_toolbar_title.text = "网络连接"
        iv_toolbar_back.setOnClickListener(this)

        tv_ping.setOnClickListener(this)
    }

    private fun excuteCommand() {
        val ip: String = et_input_ping.text.toString().trim()
        if (!TextUtils.isEmpty(ip)) {
            val pingResult = PingUtil.ping(3, 0.5f, ip, false)
            /*if (pingResult != null) {
                if (pingResult.getCode() == 0) {
                    mResultTv.setText(pingResult.successMsg);
                } else {
                    mResultTv.setText(pingResult.successMsg);
                }
            }*/MLogger.i(TAG, "result : $pingResult")
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