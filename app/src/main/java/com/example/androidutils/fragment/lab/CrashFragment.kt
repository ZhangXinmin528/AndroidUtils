package com.example.androidutils.fragment.lab

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentCrashBinding
import com.zxm.utils.core.crash.CrashInfo
import com.zxm.utils.core.crash.CrashManager
import com.zxm.utils.core.dialog.DialogUtil

/**
 * Created by ZhangXinmin on 2019/8/12.
 * Copyright (c) 2019 . All rights reserved.
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.Lab, funcName = "崩溃信息", funcIconRes = R.drawable.icon_crash_catch)
class CrashFragment : BaseFragment(), View.OnClickListener {
    private lateinit var crashBinding: FragmentCrashBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        crashBinding = FragmentCrashBinding.inflate(inflater, container, false)
        return crashBinding.root
    }

    override fun initParamsAndValues() {
        crashBinding.layoutTitle.tvToolbarTitle.text = "崩溃信息"
        crashBinding.layoutTitle.ivToolbarBack.setOnClickListener(this)

        crashBinding.switchCrash.setOnCheckedChangeListener { buttonView, isChecked ->
            Toast.makeText(activity, "状态 ： $isChecked", Toast.LENGTH_SHORT).show()
            if (isChecked) {
                CrashManager.getInstance().startCapture()
            } else {
                CrashManager.getInstance().stopCapture()
            }
        }

        crashBinding.tvCrashFileSize.text = CrashManager.getInstance().crashFilesMemorySize
        val list: MutableList<CrashInfo> = CrashManager.getInstance().crashCaches

        crashBinding.tvCrashRecent.text = if (list.isEmpty()) {
            getString(R.string.all_crash_empty_record)
        } else {
            list[0].tr
        }

        crashBinding.layoutClearCrash.setOnClickListener(this)
        crashBinding.tvImitateCrash.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }
            R.id.layout_clear_crash -> DialogUtil.showDialog(
                mContext!!,
                "是否清除崩溃日志信息？",
                true
            ) { dialog, which ->
                val state = CrashManager.getInstance().clearCrashFiles()
                if (state) {
                    crashBinding.tvCrashFileSize.text =
                        CrashManager.getInstance().crashFilesMemorySize
                }
            }
            R.id.tv_imitate_crash -> imitateCrash()
        }
    }

    private fun imitateCrash() {
        val nullTv: TextView? = null
        nullTv!!.text = ""
    }
}