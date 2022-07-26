package com.example.androidutils.fragment.component

import android.annotation.SuppressLint
import android.view.View
import android.widget.SeekBar
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.zxm.utils.core.bar.StatusBarCompat
import kotlinx.android.synthetic.main.fragment_status_bar.*
import kotlinx.android.synthetic.main.layout_toolbar_back.*

/**
 * Created by ZhangXinmin on 2021/06/16.
 * Copyright (c) 2021/6/16 . All rights reserved.
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.Component, funcName = "状态栏", funcIconRes = R.drawable.icon_status_bar)
class StatusBarFragment : BaseFragment(), View.OnClickListener {

    override fun setLayoutId(): Int {
        return R.layout.fragment_status_bar
    }

    override fun initParamsAndValues() {

    }

    override fun initViews(rootView: View) {
        tv_toolbar_title.text = "状态栏"
        iv_toolbar_back.setOnClickListener(this)

        tv_bar_info.text = getStatusBarInfo()

        switch_mode.isChecked = StatusBarCompat.isStatusBarLightMode(activity!!)
        switch_mode.setOnCheckedChangeListener { buttonView, isChecked ->
            StatusBarCompat.setStatusBarLightMode(activity!!, isChecked)
            tv_bar_info.text = getStatusBarInfo()
        }

        seekbar.max = 255
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                StatusBarCompat.setColor(
                    activity,
                    resources.getColor(R.color.colorPrimary),
                    progress
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    private fun getStatusBarInfo(): String {
        val sb = StringBuilder()
        sb.append("状态栏信息:")
            .append("\n状态栏是否显示：${StatusBarCompat.isStatusBarVisible(activity!!)}")
            .append("\n状态栏高度：${StatusBarCompat.getStatusBarHeight(mContext!!)}")
            .append("\nLight Mode：${StatusBarCompat.isStatusBarLightMode(activity!!)}")

        return sb.toString()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        StatusBarCompat.setColor(
            activity,
            resources.getColor(R.color.colorPrimary),
            0
        )

        StatusBarCompat.setStatusBarLightMode(activity!!, false)
        super.onDestroyView()
    }
}