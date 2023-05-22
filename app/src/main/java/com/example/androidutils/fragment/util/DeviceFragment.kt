package com.example.androidutils.fragment.util

import android.Manifest
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentDeviceBinding
import com.zxm.utils.core.device.DeviceUtil
import com.zxm.utils.core.permission.PermissionUtils
import java.util.*

/**
 * Created by ZhangXinmin on 2018/11/23.
 * Copyright (c) 2018 . All rights reserved.
 * 查看设备信息
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.UTILS, funcName = "设备信息", funcIconRes = R.mipmap.icon_device_info)
class DeviceFragment : BaseFragment(), View.OnClickListener {

    private lateinit var deviceBinding: FragmentDeviceBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        deviceBinding = FragmentDeviceBinding.inflate(inflater, container, false)
        return deviceBinding.root
    }

    override fun initParamsAndValues() {
        if (!PermissionUtils.checkPersmission(mContext!!, Manifest.permission.READ_PHONE_STATE)) {
            PermissionUtils.requestPermissions(
                activity!!, arrayOf(Manifest.permission.READ_PHONE_STATE), 1001
            )
        }

        deviceBinding.layoutTitle.tvToolbarTitle.text = "设备信息"
        deviceBinding.layoutTitle.ivToolbarBack.setOnClickListener(this)

        if (DeviceUtil.isDeviceRooted()) {
            deviceBinding.layoutReboot.visibility = View.VISIBLE
        } else {
            deviceBinding.layoutReboot.visibility = View.GONE
        }

        deviceBinding.tvDeviceDetial.text = getDeviceDetial()

        deviceBinding.btnReboot.setOnClickListener(this)
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceDetial(): String {
        val sb = StringBuilder()
        sb.append("设备是否Root：").append(DeviceUtil.isDeviceRooted()).append("\n")
        sb.append("\n")
        sb.append("系统版本号：").append(DeviceUtil.getDisplayID()).append("\n")
        sb.append("产品型号：").append(DeviceUtil.getProductName()).append("\n")
        sb.append("产品工业型号：").append(DeviceUtil.getDeviceInfo()).append("\n")
        sb.append("\n")
        sb.append("主板信息：").append(DeviceUtil.getBoardInfo()).append("\n")
        sb.append("设备ABIs：").append(Arrays.asList(*DeviceUtil.getABIs())).append("\n")
        sb.append("产品/硬件制造商：").append(DeviceUtil.getManufacturer()).append("\n")
        sb.append("产品/硬件品牌：").append(DeviceUtil.getBrandInfo()).append("\n")
        sb.append("产品最终型号：").append(DeviceUtil.getModel()).append("\n")
        sb.append("系统启动程序版本号：").append(DeviceUtil.getBootLoaderInfo()).append("\n")
        sb.append("基带版本：").append(DeviceUtil.getRadioVersion()).append("\n")
        sb.append("设备序列号: ").append(DeviceUtil.getSerialNumber()).append("\n")
        sb.append("\n")
        sb.append("设备Sdk版本名：").append(DeviceUtil.getSDKVersionName()).append("\n")
        sb.append("设备Sdk版本代码：").append(DeviceUtil.getSDKVersionCode()).append("\n")
        sb.append("Android ID: ").append(DeviceUtil.getAndroidID(mContext!!)).append("\n")
        sb.append("设备Mac地址：").append(DeviceUtil.getMacAddress(mContext!!)).append("\n")
        return sb.toString()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }


            R.id.btn_reboot -> if (DeviceUtil.isDeviceRooted()) {
                DeviceUtil.reboot(mContext!!)
            } else {
                DeviceUtil.reboot(mContext!!, "")
            }
        }
    }
}