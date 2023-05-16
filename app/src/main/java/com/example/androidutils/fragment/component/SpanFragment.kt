package com.example.androidutils.fragment.component

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentSpanBinding
import com.zxm.utils.core.text.ClickableMovementMethod
import com.zxm.utils.core.text.SpanUtils

/**
 * Created by Administrator on 2018/1/16.
 * Copyright (c) 2018 . All rights reserved.
 *
 *
 * Display how to use [SpanUtils] to
 * create series of styles [android.widget.TextView].
 *
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.Component, funcName = "富文本工具", funcIconRes = R.mipmap.icon_richtext)
class SpanFragment : BaseFragment(), View.OnClickListener {
    private lateinit var spanBinding: FragmentSpanBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        spanBinding = FragmentSpanBinding.inflate(inflater, container, false)
        return spanBinding.root
    }

    override fun initParamsAndValues() {

        spanBinding.layoutTitle.tvToolbarTitle.text = "富文本工具"
        spanBinding.layoutTitle.ivToolbarBack.setOnClickListener(this)

        //引线
        val quoteBuilder =
            SpanUtils.getBuilder(mContext!!, "这是引线示例！", true).setQuoteColor(Color.BLUE).create()
        spanBinding.tvQuote.text = quoteBuilder

        //列表标记
        val bulletBuilder =
            SpanUtils.getBuilder(mContext!!, "这是列表示例！", true).setBullet(10, Color.RED).create()
        spanBinding.tvBullet.setText(bulletBuilder)

        //删除线和下划线
        val lineBuilder =
            SpanUtils.getBuilder(mContext!!, "这是删除线和下划线！", true).setBullet(10, Color.RED)
                .setUnderline().setStrikethrough().create()
        spanBinding.tvLine.setText(lineBuilder)

        //上标
        val scriptBuilder =
            SpanUtils.getBuilder(mContext!!, "这是上标", false).setSuperscript().append("th", true)
                .create()
        spanBinding.tvScript.setText(scriptBuilder)

        //粗体和斜体
        val boldBuilder =
            SpanUtils.getBuilder(mContext!!, "这是粗体和斜体", true).setBold().setBullet(10, Color.RED)
                .setItalic().create()
        spanBinding.tvBold.setText(boldBuilder)

        //点击
        val clickBuilder =
            SpanUtils.getBuilder(mContext!!, "这是点击的情况:", true).setBullet(10, Color.RED)
                .setBackgroundColor(Color.parseColor("#ededed")).append("追加第一个点击,", true)
                .setClickSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        Toast.makeText(mContext, "追加第一个点击！", Toast.LENGTH_SHORT).show()
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        ds.color = mContext!!.resources.getColor(R.color.clickspan_color)
                        ds.isUnderlineText = true
                    }
                }).setBackgroundColor(mContext!!.resources.getColor(R.color.clickspan_color))
                .append("追加第二个点击", true).setClickSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        Toast.makeText(mContext, "追加第二个点击！", Toast.LENGTH_SHORT).show()
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        ds.color = mContext!!.resources.getColor(R.color.colorAccent)
                        ds.isUnderlineText = true
                    }
                }).create()
        spanBinding.tvClick.setText(clickBuilder)
        spanBinding.tvClick.setMovementMethod(ClickableMovementMethod.getInstance())
        spanBinding.tvClick.setClickable(false)
        spanBinding.tvClick.setHighlightColor(Color.TRANSPARENT)

        //中间设置样式
        val complexBuilder = SpanUtils.getBuilder(mContext!!, "此示例展示复杂的情况：", false)
            .setFlag(Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            .setBackgroundColor(Color.parseColor("#ededed")).setTextColor(Color.RED).setBold()
            .setBullet(10, Color.RED).setStrikethrough().setUnderline().append("中间文字有样式,", true)
            .append("后面文字没有样式！", false).create()
        spanBinding.tvComplex.setText(complexBuilder)

        //富文本应用示例
        val exampleBuilder =
            SpanUtils.showBeautfulText(mContext, "富文本应用示例：看过来，看过来，看过来，看过来，看过来！", "过来")
        spanBinding.tvExample.setText(exampleBuilder)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }
        }
    }
}