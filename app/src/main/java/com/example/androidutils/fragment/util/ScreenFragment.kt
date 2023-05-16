package com.example.androidutils.fragment.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentScreenBinding
import com.zxm.utils.core.screen.ScreenUtil

/**
 * Created by ZhangXinmin on 2018/8/30.
 * Copyright (c) 2018 . All rights reserved.
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.UTILS, funcName = "屏幕相关", funcIconRes = R.mipmap.icon_screen_info)
class ScreenFragment : BaseFragment(), View.OnClickListener {


    private lateinit var screenBinding: FragmentScreenBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        screenBinding = FragmentScreenBinding.inflate(inflater, container, false)
        return screenBinding.root
    }

    override fun initParamsAndValues() {
        initViews()
    }

    fun initViews() {

        screenBinding.layoutTitle.tvToolbarTitle.text = "屏幕相关"
        screenBinding.layoutTitle.ivToolbarBack.setOnClickListener(this)

        screenBinding.tvDeviceInfo.text = getString(R.string.all_screen_info, getDeviceInfo())
        screenBinding.btnFullscreen.setOnClickListener(this)
        screenBinding.btnScreenShot.setOnClickListener(this)
    }

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
                screenBinding.ivTest.post(Runnable { screenBinding.ivTest.setImageBitmap(bitmap) })
            }
        }
    }
}