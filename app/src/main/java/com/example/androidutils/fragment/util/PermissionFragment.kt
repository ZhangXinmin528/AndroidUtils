package com.example.androidutils.fragment.util

import android.Manifest
import android.content.pm.PackageManager
import android.view.View
import androidx.core.app.ActivityCompat
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.example.androidutils.R
import com.coding.zxm.lib_core.base.BaseFragment
import com.zxm.utils.core.dialog.DialogUtil
import com.zxm.utils.core.permission.PermissionChecker
import kotlinx.android.synthetic.main.fragment_permission.*

/**
 * Created by ZhangXinmin on 2019/1/4.
 * Copyright (c) 2018 . All rights reserved.
 * 权限测试
 */
@Function(group = Group.UTILS, funcName = "权限申请", funcIconRes = R.drawable.icon_permission)
class PermissionFragment : BaseFragment(), View.OnClickListener {
    private val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_SMS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun setLayoutId(): Int {
        return R.layout.fragment_permission
    }

    override fun initViews(rootView: View) {
        btn_single.setOnClickListener(this)
        btn_multiple.setOnClickListener(this)
    }

    override fun initParamsAndValues() {}

    /**
     * check permission
     */
    private fun checkSinglePermission() {
        if (!PermissionChecker.checkPersmission(mContext!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PermissionChecker.requestPermissions(activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_EXTERNAL)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_single -> checkSinglePermission()
            R.id.btn_multiple -> checkPermissions()
        }
    }

    private fun checkPermissions() {
        if (!PermissionChecker.checkSeriesPermissions(mContext!!, permissions)) { //存在未被允许的权限
            PermissionChecker.requestPermissions(activity!!, permissions, REQUEST_PERMISSIONS)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (grantResults != null) {
            val size = grantResults.size
            for (i in 0 until size) {
                val grantResult = grantResults[i]
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    val showRequest = ActivityCompat.shouldShowRequestPermissionRationale(
                            activity!!, permissions[i])
                    if (showRequest) {
                        DialogUtil.showForceDialog(mContext!!,
                                PermissionChecker.matchRequestPermissionRationale(mContext!!, permissions[i])
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