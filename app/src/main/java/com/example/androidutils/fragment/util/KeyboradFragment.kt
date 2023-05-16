package com.example.androidutils.fragment.util

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentKeyboradBinding
import com.zxm.utils.core.keyborad.KeyboradUtil
import com.zxm.utils.core.keyborad.KeyboradUtil.OnSoftInputChangedListener
import com.zxm.utils.core.text.SpanUtils

/**
 * Created by ZhangXinmin on 2018/9/1.
 * Copyright (c) 2018 . All rights reserved.
 * User guide for [KeyboradUtil]
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.UTILS, funcName = "软键盘工具", funcIconRes = R.drawable.icon_keybroad)
class KeyboradFragment : BaseFragment(), View.OnClickListener, OnSoftInputChangedListener {
    private lateinit var keyboradBinding: FragmentKeyboradBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        keyboradBinding = FragmentKeyboradBinding.inflate(inflater, container, false)
        return keyboradBinding.root
    }

    override fun initParamsAndValues() {
        initViews()
    }

    fun initViews() {
        keyboradBinding.layoutTitle.tvToolbarTitle.text = "软键盘工具"
        keyboradBinding.layoutTitle.ivToolbarBack.setOnClickListener(this)

        keyboradBinding.btnShowKeyborad.setOnClickListener(this)
        keyboradBinding.btnHideKeyborad.setOnClickListener(this)

        KeyboradUtil.registerSoftInputChangedListener(activity!!, this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }
            R.id.btn_show_keyborad -> KeyboradUtil.showSoftInput(activity)
            R.id.btn_hide_keyborad -> activity?.let { KeyboradUtil.hideSoftInput(it) }
        }
    }

    override fun onSoftInputChanged(height: Int) {
        keyboradBinding.tvResult.text = SpanUtils.getBuilder(mContext!!, "软键盘状态改变：", false)
            .setTextColor(Color.CYAN)
            .append("软键盘是否可见：${activity?.let { KeyboradUtil.isSoftInputVisible(it) }}", true)
            .append("\n软键盘高度：$height", true)
            .create()
    }

    override fun onDestroy() {
        activity?.let {
            KeyboradUtil.unregisterSoftInputChangedListener(it)
            KeyboradUtil.hideSoftInput(it)
        }

        super.onDestroy()
    }
}