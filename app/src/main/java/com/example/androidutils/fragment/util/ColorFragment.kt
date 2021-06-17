package com.example.androidutils.fragment.util

import android.view.View
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.zxm.utils.core.color.ColorUtils
import com.zxm.utils.core.text.SpanUtils
import kotlinx.android.synthetic.main.fragment_color.*
import kotlinx.android.synthetic.main.layout_toolbar_back.*

/**
 * Created by ZhangXinmin on 2021/06/17.
 * Copyright (c) 2021/6/17 . All rights reserved.
 */
@Function(group = Group.UTILS, funcName = "颜色工具", funcIconRes = R.drawable.icon_color)
class ColorFragment : BaseFragment(), View.OnClickListener {

    override fun setLayoutId(): Int {
        return R.layout.fragment_color
    }

    override fun initParamsAndValues() {

    }

    override fun initViews(rootView: View) {
        tv_toolbar_title.text = "颜色工具"
        iv_toolbar_back.setOnClickListener(this)

        val alphaBlack = ColorUtils.setColorAlpha(resources.getColor(R.color.colorBlue), 0.5f)

        tv_color_alpha.text = SpanUtils.getBuilder(mContext!!, "Color2设置透明度(50%)：", false)
            .setTextColor(alphaBlack)
            .append(ColorUtils.colorToString(alphaBlack), true)
            .create()


        val combineColor = ColorUtils.computeColor(
            resources.getColor(R.color.colorBlue),
            resources.getColor(R.color.colorRed),
            0.5f
        )

        tv_compute_color.text = SpanUtils.getBuilder(mContext!!, "颜色合成：", false)
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