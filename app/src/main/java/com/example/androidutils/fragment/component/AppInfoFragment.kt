package com.example.androidutils.fragment.component

import android.os.Build
import android.view.View
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.zxm.utils.core.app.AppUtil
import kotlinx.android.synthetic.main.fragment_app_info.*
import kotlinx.android.synthetic.main.layout_toolbar_back.*

/**
 * Created by ZhangXinmin on 2021/06/16.
 * Copyright (c) 2021/6/16 . All rights reserved.
 */
@Function(group = Group.UTILS, funcName = "应用信息", funcIconRes = R.mipmap.icon_app)
class AppInfoFragment : BaseFragment(), View.OnClickListener {

    override fun setLayoutId(): Int {
        return R.layout.fragment_app_info
    }

    override fun initParamsAndValues() {
        tv_toolbar_title.text = "应用信息"
        iv_toolbar_back.setOnClickListener(this)

        iv_app_logo.setImageDrawable(AppUtil.getAppIcon(mContext!!))

    }

    override fun initViews(rootView: View) {
        tv_app_info.text = getAppInfo()
    }

    private fun getAppInfo(): String {
        val sb = StringBuilder()
        sb.append("应用名称：${AppUtil.getAppName(mContext!!)}")
            .append("\n应用包名：${AppUtil.getAppPackageName(mContext!!)}")
            .append("\n编译版本：${AppUtil.getAppVersionCode(mContext!!)}")
            .append("\n版本名：${AppUtil.getAppVersionName(mContext!!)}")
            .append("\n应用是否Debug版本：${AppUtil.isAppDebug(mContext!!)}")
            .append("\n是否系统应用：${AppUtil.isAppSystem(mContext!!)}")


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sb.append("\n是否前台应用：${AppUtil.isAppForeground(mContext!!)}")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sb.append("\n目标系统版本：${AppUtil.getTargetSdkVersion(mContext!!)}")
                .append("\n最小系统版本：${AppUtil.getMinSdkVersion(mContext!!)}")
        }

        return sb.toString()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }
        }
    }
}