package com.example.androidutils.fragment.util

import android.view.View
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.example.androidutils.R
import com.coding.zxm.lib_core.base.BaseFragment
import com.zxm.utils.core.screen.ScreenUtil
import kotlinx.android.synthetic.main.fragment_screen.*
import kotlinx.android.synthetic.main.layout_toolbar_back.*

/**
 * Created by ZhangXinmin on 2018/8/30.
 * Copyright (c) 2018 . All rights reserved.
 */
@Function(group = Group.UTILS, funcName = "屏幕相关", funcIconRes = R.drawable.icon_screen)
class ScreenFragment : BaseFragment(), View.OnClickListener {

    override fun setLayoutId(): Int {
        return R.layout.fragment_screen
    }

    override fun initViews(rootView: View) {

        tv_toolbar_title.text = "屏幕相关"
        iv_toolbar_back.setOnClickListener(this)

        tv_device_info.text = getString(R.string.all_screen_info, getDeviceInfo())
        btn_fullscreen.setOnClickListener(this)
        btn_screenShot.setOnClickListener(this)
    }

    override fun initParamsAndValues() {}

    /**
     * 获取设备信息
     *
     * @return
     */
    private fun getDeviceInfo(): String? {
        val sb = StringBuffer()
        sb.append("\n屏幕密度：${ScreenUtil.getScreenDensity(mContext)}")
        sb.append("\n屏幕DPI：${ScreenUtil.getScreenDensityDpi(mContext)}")
        sb.append("\n屏幕宽度（像素）：${ScreenUtil.getScreenWidth(mContext)}")
        sb.append("\n屏幕高度（像素）：${ScreenUtil.getScreenHeight(mContext)}")
        sb.append("\n屏幕物理尺寸(英寸)：${ScreenUtil.getPhysicsScreenSize(mContext)}")
        sb.append("\n屏幕物理宽度(英寸)：${ScreenUtil.getPhysicsScreenWidth(mContext)}")
        sb.append("\n屏幕物理高度(英寸)：${ScreenUtil.getPhysicsScreenHeight(mContext)}")
        sb.append("\n是否横屏：${ScreenUtil.isLandscape(mContext)}")
        sb.append("\n屏幕旋转角度：${ScreenUtil.getScreenRotation(activity!!)}")
        sb.append("\n是否锁屏：${ScreenUtil.isScreenLock(mContext)}")
        sb.append("\n是否平板：${ScreenUtil.isTablet(mContext)}")
        return sb.toString()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }
            R.id.btn_fullscreen -> ScreenUtil.setFullScreen(activity!!)
            R.id.btn_screenShot -> {
                val bitmap = ScreenUtil.screenShot(activity!!)
                iv_test.post(Runnable { iv_test.setImageBitmap(bitmap) })
            }
        }
    }
}