package com.example.androidutils.fragment.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentColorBinding
import com.zxm.utils.core.color.ColorUtils
import com.zxm.utils.core.text.SpanUtils

/**
 * Created by ZhangXinmin on 2021/06/17.
 * Copyright (c) 2021/6/17 . All rights reserved.
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.UTILS, funcName = "颜色工具", funcIconRes = R.mipmap.icon_color)
class ColorFragment : BaseFragment(), View.OnClickListener {
    private lateinit var colorBinding: FragmentColorBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        colorBinding = FragmentColorBinding.inflate(inflater, container, false)
        return colorBinding.root
    }

    override fun initParamsAndValues() {
        colorBinding.layoutTitle.tvToolbarTitle.text = "颜色工具"
        colorBinding.layoutTitle.ivToolbarBack.setOnClickListener(this)

        val alphaBlack = ColorUtils.setColorAlpha(resources.getColor(R.color.colorBlue), 0.5f)

        colorBinding.tvColorAlpha.text =
            SpanUtils.getBuilder(mContext!!, "Color2设置透明度(50%)：", false)
                .setTextColor(alphaBlack)
                .append(ColorUtils.colorToString(alphaBlack), true)
                .create()


        val combineColor = ColorUtils.computeColor(
            resources.getColor(R.color.colorBlue),
            resources.getColor(R.color.colorRed),
            0.5f
        )

        colorBinding.tvComputeColor.text = SpanUtils.getBuilder(mContext!!, "颜色合成：", false)
            .setTextColor(combineColor)
            .append(ColorUtils.colorToString(combineColor), true)
            .create()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }
        }
    }
}