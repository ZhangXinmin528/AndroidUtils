package com.example.androidutils.fragment.util

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentSettingBinding
import com.zxm.utils.core.setting.SettingUtils

/**
 * Created by ZhangXinmin on 2019/6/11.
 * Copyright (c) 2018 . All rights reserved.
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.UTILS, funcName = "系统设置", funcIconRes = R.mipmap.icon_setting)
class SettingFragment : BaseFragment(), View.OnClickListener {

    companion object {
        private const val REQUEST_UNKNOWN_SOURCE = 1001
    }

    private lateinit var settingBinding: FragmentSettingBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        settingBinding = FragmentSettingBinding.inflate(inflater, container, false)
        return settingBinding.root
    }

    override fun initParamsAndValues() {
        initViews()
    }

    fun initViews() {
        settingBinding.layoutTitle.tvToolbarTitle.text = "系统设置"
        settingBinding.layoutTitle.ivToolbarBack.setOnClickListener(this)

        settingBinding.tvAppDetial.setOnClickListener(this)
        settingBinding.tvSelfStart.setOnClickListener(this)
        settingBinding.tvSettingInstallNotMarket.setOnClickListener(this)
        settingBinding.tvSettingNfc.setOnClickListener(this)
        settingBinding.tvSettingWifi.setOnClickListener(this)
        settingBinding.tvSettingBluetooth.setOnClickListener(this)
        settingBinding.tvSettingAccessibility.setOnClickListener(this)
        settingBinding.tvSettingApplication.setOnClickListener(this)
        settingBinding.tvSettingAppDev.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }
            R.id.tv_app_detial -> SettingUtils.openAppDetial(mContext!!)
            R.id.tv_self_start -> SettingUtils.openSelfStartPage(mContext!!)
//            R.id.tv_setting_install_not_market -> installUnknownSourceApp()
            R.id.tv_setting_wifi -> SettingUtils.openWifiSetting(mContext!!)
            R.id.tv_setting_nfc -> SettingUtils.openNfcSetting(mContext!!)
            R.id.tv_setting_bluetooth -> SettingUtils.openBluetoothSetting(mContext!!)
            R.id.tv_setting_accessibility -> SettingUtils.openAccessibilitySetting(mContext!!)
            R.id.tv_setting_application -> SettingUtils.openApplicationSetting(mContext!!)
            R.id.tv_setting_app_dev -> SettingUtils.openApplicationDevelopmentSetting(mContext!!)
        }
    }

    private fun installUnknownSourceApp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val packageManager = mContext!!.packageManager
            if (packageManager != null) {
                val hasPermission = packageManager.canRequestPackageInstalls()
                if (!hasPermission) {
                    AlertDialog.Builder(mContext!!)
                        .setTitle("允许安装未知来源应用？")
                        .setMessage(R.string.all_install_unknown_source_app)
                        .setNegativeButton(android.R.string.cancel) { dialog, which ->

                        }.setPositiveButton(android.R.string.ok) { dialog, which ->
                            SettingUtils.openUnknownAppSourcesSetting(
                                activity!!,
                                REQUEST_UNKNOWN_SOURCE
                            )
                        }.show()
                }
            }
        }
    }

//    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        when (requestCode) {
//            REQUEST_UNKNOWN_SOURCE -> if (resultCode == Activity.RESULT_OK) {
//                Toast.makeText(mContext, "可以安装未知来源应用", Toast.LENGTH_SHORT).show()
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }

}