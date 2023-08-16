package com.example.androidutils.fragment.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentTvBinding

/**
 * Created by zhangxinmin on 2023/7/17.
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.UTILS, funcName = "电视相关", funcIconRes = R.mipmap.icon_tv)
class TvFragment : BaseFragment() {

    private lateinit var tvBinding: FragmentTvBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        tvBinding = FragmentTvBinding.inflate(inflater, container, false)
        return tvBinding.root
    }

    override fun initParamsAndValues() {

    }
}