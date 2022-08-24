package com.example.androidutils.fragment.lab

import android.view.View
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.zxm.utils.core.widget.loginfo.LogInfoPanel
import kotlinx.android.synthetic.main.layout_toolbar_back.*

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

    override fun setLayoutId(): Int {
        return R.layout.fragment_log_info
    }

    override fun initParamsAndValues() {
    }

    override fun initViews(rootView: View) {
        tv_toolbar_title.text = "日志信息"
        iv_toolbar_back.setOnClickListener(this)

        LogInfoPanel(requireContext())
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }
        }
    }
}