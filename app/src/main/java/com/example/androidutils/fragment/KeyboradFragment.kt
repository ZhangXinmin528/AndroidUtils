package com.example.androidutils.fragment

import android.graphics.Color
import android.view.View
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.example.androidutils.R
import com.example.androidutils.base.BaseFragment
import com.zxm.utils.core.keyborad.KeyboradUtil
import com.zxm.utils.core.keyborad.KeyboradUtil.OnSoftInputChangedListener
import com.zxm.utils.core.text.SpanUtils
import kotlinx.android.synthetic.main.fragment_keyborad.*

/**
 * Created by ZhangXinmin on 2018/9/1.
 * Copyright (c) 2018 . All rights reserved.
 * User guide for [KeyboradUtil]
 */
@Function(group = Group.Component, funcName = "软键盘工具", funcIconRes = R.drawable.icon_keybroad)
class KeyboradFragment : BaseFragment(), View.OnClickListener, OnSoftInputChangedListener {
    override fun setLayoutId(): Int {
        return R.layout.fragment_keyborad
    }

    override fun initParamsAndValues() {}
    override fun initViews(rootView: View) {
        btn_show_keyborad.setOnClickListener(this)
        btn_hide_keyborad.setOnClickListener(this)

        activity?.let { KeyboradUtil.registerSoftInputChangedListener(it, this) }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_show_keyborad -> KeyboradUtil.showSoftInput(activity)
            R.id.btn_hide_keyborad -> activity?.let { KeyboradUtil.hideSoftInput(it) }
        }
    }

    override fun onSoftInputChanged(height: Int) {
        tv_result.text = SpanUtils.getBuilder(mContext!!, "软键盘状态改变：", false)
                .setTextColor(Color.CYAN)
                .append("""
    
    软键盘是否可见：${activity?.let { KeyboradUtil.isSoftInputVisible(it) }}
    """.trimIndent(), true)
                .append("\n软键盘高度：$height", true)
                .create()
    }


    override fun onDestroy() {
        activity?.let { KeyboradUtil.unregisterSoftInputChangedListener(it) }
        super.onDestroy()
    }
}