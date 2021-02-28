package com.example.androidutils.fragment

import android.graphics.BitmapFactory
import android.view.View
import androidx.palette.graphics.Palette
import com.example.androidutils.R
import com.example.androidutils.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_palette.*

/**
 * Created by ZhangXinmin on 2018/11/20.
 * Copyright (c) 2018.
 * 取色功能
 */
class PaletteFrameng : BaseFragment() {

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