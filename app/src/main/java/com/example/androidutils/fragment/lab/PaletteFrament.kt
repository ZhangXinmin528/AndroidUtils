package com.example.androidutils.fragment.lab

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.palette.graphics.Palette
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentPaletteBinding

/**
 * Created by ZhangXinmin on 2018/11/20.
 * Copyright (c) 2018.
 * 取色功能
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.Lab, funcName = "图片取色", funcIconRes = R.drawable.icon_color_picker)
class PaletteFrament : BaseFragment(), View.OnClickListener {

    private lateinit var paletteBinding: FragmentPaletteBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        paletteBinding = FragmentPaletteBinding.inflate(inflater, container, false)
        return paletteBinding.root
    }

    override fun initParamsAndValues() {
        paletteBinding.layoutTitle.tvToolbarTitle.text = "图片取色"
        paletteBinding.layoutTitle.tvToolbarTitle.setOnClickListener(this)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.palette_simple)
        if (bitmap != null) {
            paletteBinding.ivPaletteSimple.setImageBitmap(bitmap)
            Palette.from(bitmap).generate { palette ->
                if (palette != null) {
                    //
                    val swatch = palette.vibrantSwatch
                    val bgColor = swatch!!.rgb
                    val bodyColor = swatch.bodyTextColor
                    val titleColor = swatch.titleTextColor

                    paletteBinding.tvPaletteSimple.setBackgroundColor(bgColor)
                    paletteBinding.tvPaletteSimple.setTextColor(titleColor)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }
        }
    }
}