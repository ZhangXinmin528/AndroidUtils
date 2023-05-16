package com.example.androidutils.fragment.lab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentLogInfoBinding
import com.zxm.utils.core.permission.PermissionChecker
import com.zxm.utils.core.widget.loginfo.LogInfoKit

/**
 * Created by ZhangXinmin on 2022/08/24.
 * Copyright (c) 2022/8/24 . All rights reserved.
 */
@Function(group = Group.Lab, funcName = "日志信息", funcIconRes = R.drawable.icon_log_info)
class LogInfoFragment : BaseFragment(), View.OnClickListener {

    companion object {
        fun newInstance(): LogInfoFragment {
            return LogInfoFragment()
        }
    }

    private lateinit var logInfoBinding: FragmentLogInfoBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        logInfoBinding = FragmentLogInfoBinding.inflate(inflater, container, false)
        return logInfoBinding.root
    }

    override fun initParamsAndValues() {
        checkCanDrawOverlay()

        logInfoBinding.layoutTitle.tvToolbarTitle.text = "日志信息"
        logInfoBinding.layoutTitle.ivToolbarBack.setOnClickListener(this)

        logInfoBinding.btnShowLog.setOnClickListener(this)
        logInfoBinding.btnRemoveLog.setOnClickListener(this)
        logInfoBinding.btnMaxLog.setOnClickListener(this)
        logInfoBinding.btnMinLog.setOnClickListener(this)
    }

    private fun checkCanDrawOverlay() {
        if (!PermissionChecker.canDrawOverlays(mContext)) {
            PermissionChecker.requestDrawOverlays(mContext)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }
            R.id.btn_show_log -> {
                LogInfoKit.getInstance().onAttach(requireContext())
            }
            R.id.btn_remove_log -> {
                LogInfoKit.getInstance().onDetach()
            }
            R.id.btn_max_log -> {
                LogInfoKit.getInstance().maxPanel()
            }
            R.id.btn_min_log -> {
                LogInfoKit.getInstance().minPannel()
            }
        }
    }

}