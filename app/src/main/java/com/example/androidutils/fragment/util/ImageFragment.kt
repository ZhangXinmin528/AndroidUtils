package com.example.androidutils.fragment.util

import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.example.androidutils.R
import com.example.androidutils.adapter.ImageAdapter
import com.example.androidutils.base.BaseFragment
import com.example.androidutils.bean.ImageEntity
import com.zxm.utils.core.image.ImageUtil
import kotlinx.android.synthetic.main.fragment_image.*
import java.util.*

/**
 * Created by ZhangXinmin on 2018/9/1.
 * Copyright (c) 2018 . All rights reserved.
 * User guide for[ImageUtil]
 */
@Function(group = Group.UTILS, funcName = "图片工具", funcIconRes = R.drawable.icon_picture)
class ImageFragment : BaseFragment() {
    private val mResources: Resources? = null
    private val mList: MutableList<ImageEntity> = ArrayList()
    override fun setLayoutId(): Int {
        return R.layout.fragment_image
    }

    override fun initViews(rootView: View) {
        val src = ImageUtil.getBitmap(mContext!!, R.drawable.img_lena)
        val round = ImageUtil.getBitmap(mContext!!, R.drawable.avatar_round)
        val watermark = ImageUtil.getBitmap(mContext!!, R.mipmap.ic_launcher)
        val width = src.width
        val height = src.height
        mList.add(ImageEntity(mResources, R.string.image_src, src))
        mList.add(ImageEntity(mResources, R.string.image_add_color, ImageUtil.drawColor(src, Color.parseColor("#8000FF00"))))
        mList.add(ImageEntity(mResources, R.string.image_scale, ImageUtil.scale(src, width / 2, height / 2)))
        mList.add(ImageEntity(mResources, R.string.image_clip, ImageUtil.clip(src, (width - width / 2) / 2, (height - height / 2) / 2, width / 2, height / 2)))
        mList.add(ImageEntity(mResources, R.string.image_skew, ImageUtil.skew(src, 0.2f, 0.1f)))
        mList.add(ImageEntity(mResources, R.string.image_rotate, ImageUtil.rotate(src, 90, width / 2.toFloat(), height / 2.toFloat())))
        mList.add(ImageEntity(mResources, R.string.image_to_round, ImageUtil.toRound(src)))
        mList.add(ImageEntity(mResources, R.string.image_to_round_border, ImageUtil.toRound(src, 16, Color.GREEN)))
        mList.add(ImageEntity(mResources, R.string.image_to_round_corner, ImageUtil.toRoundCorner(src, 80f)))
        mList.add(ImageEntity(mResources, R.string.image_to_round_corner_border, ImageUtil.toRoundCorner(src, 80f, 16, Color.GREEN)))
        mList.add(ImageEntity(mResources, R.string.image_add_corner_border, ImageUtil.addCornerBorder(src, 16, Color.GREEN, 0f)))
        mList.add(ImageEntity(mResources, R.string.image_add_circle_border, ImageUtil.addCircleBorder(round, 16, Color.GREEN)))
        mList.add(ImageEntity(mResources, R.string.image_add_reflection, ImageUtil.addReflection(src, 80)))
        mList.add(ImageEntity(mResources, R.string.image_add_text_watermark, ImageUtil.addTextWatermark(src, "blankj", 40, Color.GREEN, 0f, 0f)))
        mList.add(ImageEntity(mResources, R.string.image_add_image_watermark, ImageUtil.addImageWatermark(src, watermark, 0, 0, 0x88)))
        mList.add(ImageEntity(mResources, R.string.image_to_gray, ImageUtil.toGray(src)))
        mList.add(ImageEntity(mResources, R.string.image_fast_blur, ImageUtil.fastBlur(mContext!!, src, 0.1f, 5f)))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mList.add(ImageEntity(mResources, R.string.image_render_script_blur, ImageUtil.renderScriptBlur(mContext!!, src, 10f)))
        }
        mList.add(ImageEntity(mResources, R.string.image_stack_blur, ImageUtil.stackBlur(src, 10)))
        mList.add(ImageEntity(mResources, R.string.image_compress_by_scale, ImageUtil.compressByScale(src, 0.5f, 0.5f)))
        mList.add(ImageEntity(mResources, R.string.image_compress_by_quality_half, ImageUtil.compressByQuality(src, 50)))
        mList.add(ImageEntity(mResources, R.string.image_compress_by_quality_max_size, ImageUtil.compressByQuality(src, 10L * 1024))) // 10Kb
        mList.add(ImageEntity(mResources, R.string.image_compress_by_sample_size, ImageUtil.compressBySampleSize(src, 2)))
        val adapter = ImageAdapter(mContext, mList)
        rv_image.adapter = adapter
        rv_image.layoutManager = LinearLayoutManager(activity)

    }

    override fun initParamsAndValues() {}
}