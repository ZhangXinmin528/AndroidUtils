package com.example.androidutils.fragment.lab

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.zxm.utils.core.crash.CrashManager
import com.zxm.utils.core.dialog.DialogUtil
import kotlinx.android.synthetic.main.fragment_crash.*
import kotlinx.android.synthetic.main.layout_toolbar_back.*

/**
 * Created by ZhangXinmin on 2019/8/12.
 * Copyright (c) 2019 . All rights reserved.
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.Lab, funcName = "崩溃信息", funcIconRes = R.drawable.icon_crash_catch)
class CrashFragment : BaseFragment(), View.OnClickListener {
    override fun setLayoutId(): Int {
        return R.layout.fragment_crash
    }

    override fun initParamsAndValues() {}

    override fun initViews(rootView: View) {
        tv_toolbar_title.text = "崩溃信息"
        iv_toolbar_back.setOnClickListener(this)

        switch_crash.setOnCheckedChangeListener { buttonView, isChecked ->
            Toast.makeText(activity, "状态 ： $isChecked", Toast.LENGTH_SHORT).show()
            if (isChecked) {
                CrashManager.getInstance().startCapture()
            } else {
                CrashManager.getInstance().stopCapture()
            }
        }
        rv_crash_result.layoutManager = LinearLayoutManager(activity)

        tv_crash_file_size.text = CrashManager.getInstance().crashFilesMemorySize

        layout_clear_crash.setOnClickListener(this)
        tv_imitate_crash.setOnClickListener(this)
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
                    tv_crash_file_size.text = CrashManager.getInstance().crashFilesMemorySize
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