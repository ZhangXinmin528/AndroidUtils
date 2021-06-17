package com.example.androidutils.fragment.util

import android.Manifest
import android.annotation.SuppressLint
import android.view.View
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.zxm.utils.core.device.DeviceUtil
import com.zxm.utils.core.permission.PermissionChecker
import kotlinx.android.synthetic.main.fragment_device.*
import kotlinx.android.synthetic.main.layout_toolbar_back.*
import java.util.*

/**
 * Created by ZhangXinmin on 2018/11/23.
 * Copyright (c) 2018 . All rights reserved.
 * 查看设备信息
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.UTILS, funcName = "设备信息", funcIconRes = R.drawable.icon_device_info)
class DeviceFragment : BaseFragment(), View.OnClickListener {

    override fun setLayoutId(): Int {
        return R.layout.fragment_device
    }

    override fun initViews(rootView: View) {
        tv_toolbar_title.text = "设备信息"
        iv_toolbar_back.setOnClickListener(this)

        if (DeviceUtil.isDeviceRooted()) {
            layout_reboot.visibility = View.VISIBLE
        } else {
            layout_reboot.visibility = View.GONE
        }

        tv_device_detial.text = getDeviceDetial()

        btn_reboot.setOnClickListener(this)
    }

    override fun initParamsAndValues() {
        if (!PermissionChecker.checkPersmission(mContext!!, Manifest.permission.READ_PHONE_STATE)) {
            PermissionChecker.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                1001
            )
        }
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


                    R . id . btn_reboot -> if (DeviceUtil.isDeviceRooted()) {
                DeviceUtil.reboot(mContext!!)
            } else {
                DeviceUtil.reboot(mContext!!, "")
            }
        }
    }
}