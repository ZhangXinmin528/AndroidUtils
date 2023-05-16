package com.example.androidutils.fragment.component

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentStatusBarBinding
import com.zxm.utils.core.bar.StatusBarCompat

/**
 * Created by ZhangXinmin on 2021/06/16.
 * Copyright (c) 2021/6/16 . All rights reserved.
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.Component, funcName = "状态栏", funcIconRes = R.drawable.icon_status_bar)
class StatusBarFragment : BaseFragment(), View.OnClickListener {
    private lateinit var statusBarBinding: FragmentStatusBarBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        statusBarBinding = FragmentStatusBarBinding.inflate(inflater, container, false)
        return statusBarBinding.root
    }

    override fun initParamsAndValues() {
        statusBarBinding.layoutTitle.tvToolbarTitle.text = "状态栏"
        statusBarBinding.layoutTitle.ivToolbarBack.setOnClickListener(this)

        statusBarBinding.tvBarInfo.text = getStatusBarInfo()

        statusBarBinding.switchMode.isChecked = StatusBarCompat.isStatusBarLightMode(activity!!)
        statusBarBinding.switchMode.setOnCheckedChangeListener { buttonView, isChecked ->
            StatusBarCompat.setStatusBarLightMode(activity!!, isChecked)
            statusBarBinding.tvBarInfo.text = getStatusBarInfo()
        }

        statusBarBinding.seekbar.max = 255
        statusBarBinding.seekbar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
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