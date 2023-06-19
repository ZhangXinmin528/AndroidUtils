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
import com.example.androidutils.databinding.FragmentPinyinBinding
import com.zxm.utils.core.pinyin.PinyinUtils
import com.zxm.utils.core.text.SpanUtils

/**
 * Created by ZhangXinmin.
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.UTILS, funcName = "拼音相关", funcIconRes = R.mipmap.icon_pinyin)
class PinyinFragment : BaseFragment() {

    private lateinit var pinyinBinding: FragmentPinyinBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        pinyinBinding = FragmentPinyinBinding.inflate(inflater, container, false)
        return pinyinBinding.root
    }

    override fun initParamsAndValues() {
        initViews()
    }

    private fun initViews() {
        pinyinBinding.layoutTitle.tvToolbarTitle.text = "拼音相关"
        pinyinBinding.layoutTitle.ivToolbarBack.setOnClickListener {
            popBackStack()
        }

        val inputText1 = "我是中国人"

        val inputBuilder1 = SpanUtils.getBuilder(requireContext(), inputText1, false).setBold()
            .setBackgroundColor(Color.CYAN).append(PinyinUtils.ccs2Pinyin(inputText1), true)
            .create()

        pinyinBinding.tvChs2pinyin.text = inputBuilder1

        val inputText2 = "移动开发者"

        val inputBuilder2 = SpanUtils.getBuilder(requireContext(), inputText2, false).setBold()
            .setBackgroundColor(Color.CYAN)
            .append(PinyinUtils.getPinyinFirstLetter(inputText2), true).create()

        pinyinBinding.tvGetFirstLetter.text = inputBuilder2
    }

}