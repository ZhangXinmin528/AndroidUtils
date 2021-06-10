package com.example.androidutils.fragment.util

import android.view.View
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.example.androidutils.R
import com.coding.zxm.lib_core.base.BaseFragment
import com.zxm.utils.core.screen.ScreenUtil
import kotlinx.android.synthetic.main.fragment_screen.*

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
        sb.append("屏幕密度：${ScreenUtil.getScreenDensity(mContext)}".trimIndent())
        sb.append("屏幕DPI：${ScreenUtil.getScreenDensityDpi(mContext)}".trimIndent())
        sb.append(" 屏幕宽度：${ScreenUtil.getScreenWidth(mContext)}".trimIndent())
        sb.append(" 屏幕高度：${ScreenUtil.getScreenHeight(mContext)}".trimIndent())
        sb.append("屏幕物理尺寸(英寸)：${ScreenUtil.getPhysicsScreenSize(mContext)}".trimIndent())
        sb.append(" 屏幕物理宽度(cm)：${ScreenUtil.getPhysicsScreenWidth(mContext)}".trimIndent())
        sb.append("屏幕物理高度(cm)：${ScreenUtil.getPhysicsScreenHeight(mContext)}".trimIndent())
        sb.append("是否横屏：${ScreenUtil.isLandscape(mContext)}".trimIndent())
        sb.append(" 屏幕旋转角度：${ScreenUtil.getScreenRotation(activity!!)}".trimIndent())
        sb.append("是否锁屏：${ScreenUtil.isScreenLock(mContext)}".trimIndent())
        sb.append("是否平板：${ScreenUtil.isTablet(mContext)}".trimIndent())
        return sb.toString()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_fullscreen -> ScreenUtil.setFullScreen(activity!!)
            R.id.btn_screenShot -> {
                val bitmap = ScreenUtil.screenShot(activity!!)
                iv_test.post(Runnable { iv_test.setImageBitmap(bitmap) })
            }
        }
    }
}