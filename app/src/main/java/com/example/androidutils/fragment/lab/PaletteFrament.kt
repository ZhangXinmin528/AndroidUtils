package com.example.androidutils.fragment.lab

import android.graphics.BitmapFactory
import android.view.View
import androidx.palette.graphics.Palette
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.example.androidutils.R
import com.coding.zxm.lib_core.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_palette.*

/**
 * Created by ZhangXinmin on 2018/11/20.
 * Copyright (c) 2018.
 * 取色功能
 */
@Function(group = Group.Lab, funcName = "图片取色", funcIconRes = R.drawable.icon_palette)
class PaletteFrament : BaseFragment() {

    override fun setLayoutId(): Int {
        return R.layout.fragment_palette
    }

    override fun initViews(rootView: View) {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.palette_simple)
        if (bitmap != null) {
            iv_palette_simple!!.setImageBitmap(bitmap)
            Palette.from(bitmap).generate { palette ->
                if (palette != null) {
                    //
                    val swatch = palette.vibrantSwatch
                    val bgColor = swatch!!.rgb
                    val bodyColor = swatch.bodyTextColor
                    val titleColor = swatch.titleTextColor

                    tv_palette_simple.setBackgroundColor(bgColor)
                    tv_palette_simple.setTextColor(titleColor)
                }
            }
        }
    }

    override fun initParamsAndValues() {}
}