package com.example.androidutils.fragment.util

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.annotation.RequiresApi
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentBrightnessBinding
import com.zxm.utils.core.brightness.BrightnessUtils
import com.zxm.utils.core.permission.PermissionUtils

/**
 * Created by ZhangXinmin on 2023/5/10.
 * Copyright (c) 2023 . All rights reserved.
 */
@SuppressLint("NonConstantResourceId")
@RequiresApi(Build.VERSION_CODES.M)
@Function(group = Group.UTILS, funcName = "亮度调节", funcIconRes = R.mipmap.icon_brightness)
class BrightnessFragment : BaseFragment() {


    companion object {
        const val REQUEST_CODE = 1001
    }

    private lateinit var brightnessBinding: FragmentBrightnessBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        brightnessBinding = FragmentBrightnessBinding.inflate(inflater, container, false)
        return brightnessBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun initParamsAndValues() {

        checkAndRequestWriteSetting()

        brightnessBinding.layoutTitle.tvToolbarTitle.text = "屏幕亮度"
        brightnessBinding.layoutTitle.ivToolbarBack.setOnClickListener {
            popBackStack()
        }

        activity?.let {
            brightnessBinding.tvGetBrightness.text =
                "当前屏幕亮度：${BrightnessUtils.getBrightness(it.application)}"


            brightnessBinding.seekbarBrightness.max = 255
            brightnessBinding.seekbarBrightness.progress =
                BrightnessUtils.getBrightness(it.application)

            brightnessBinding.switchAuto.isChecked =
                BrightnessUtils.isAutoBrightnessEnable(it.application)


        }

        brightnessBinding.seekbarBrightness.setOnSeekBarChangeListener(object :
            OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                activity?.let {
                    BrightnessUtils.setBrightness(it.application, progress)
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        brightnessBinding.switchAuto.setOnCheckedChangeListener { _, isChecked ->
            activity?.let {
                BrightnessUtils.setAutoBrightnessEnable(it.application, isChecked)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkAndRequestWriteSetting() {
        activity?.let {
            if (!PermissionUtils.canWriteSetting(it.application)) {
                PermissionUtils.requestWriteSettingPermission(it, REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

}