package com.example.androidutils.fragment.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentPermissionBinding
import com.zxm.utils.core.dialog.DialogUtil
import com.zxm.utils.core.permission.PermissionUtils

/**
 * Created by ZhangXinmin on 2019/1/4.
 * Copyright (c) 2018 . All rights reserved.
 * 权限测试
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.UTILS, funcName = "权限申请", funcIconRes = R.mipmap.icon_permissions)
class PermissionFragment : BaseFragment(), View.OnClickListener {
    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_SMS,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private lateinit var permissionBinding: FragmentPermissionBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        permissionBinding = FragmentPermissionBinding.inflate(layoutInflater, container, false)
        return permissionBinding.root
    }

    override fun initParamsAndValues() {
        initViews()
    }

    fun initViews() {
        permissionBinding.layoutTitle.tvToolbarTitle.text = "权限申请"
        permissionBinding.layoutTitle.ivToolbarBack.setOnClickListener(this)

        permissionBinding.btnSingle.setOnClickListener(this)
        permissionBinding.btnMultiple.setOnClickListener(this)
    }


    /**
     * check permission
     */
    private fun checkSinglePermission() {
        if (!PermissionUtils.checkPersmission(
                mContext!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            PermissionUtils.requestPermissions(
                activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_EXTERNAL
            )
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }
            R.id.btn_single -> checkSinglePermission()
            R.id.btn_multiple -> checkPermissions()
        }
    }

    private fun checkPermissions() {
        if (!PermissionUtils.checkSeriesPermissions(mContext!!, permissions)) { //存在未被允许的权限
            PermissionUtils.requestPermissions(activity!!, permissions, REQUEST_PERMISSIONS)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (grantResults != null) {
            val size = grantResults.size
            for (i in 0 until size) {
                val grantResult = grantResults[i]
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    val showRequest = ActivityCompat.shouldShowRequestPermissionRationale(
                        activity!!, permissions[i]
                    )
                    if (showRequest) {
                        DialogUtil.showForceDialog(
                            mContext!!,
                            PermissionUtils.matchRequestPermissionRationale(
                                mContext!!,
                                permissions[i]
                            )
                        ) { dialog, which -> }
                    }
                } else {
                    //do something
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val REQUEST_EXTERNAL = 1001
        private const val REQUEST_PERMISSIONS = 1002
    }
}