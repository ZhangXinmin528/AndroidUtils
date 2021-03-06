package com.example.androidutils.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.fragment.decorator.GridDividerItemDecoration
import com.example.androidutils.manager.FuncDataManager
import com.example.androidutils.model.FuncItemDescription
import kotlinx.android.synthetic.main.fragment_home_tab.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * Created by ZhangXinmin on 2021/3/16.
 * Copyright (c) 2021 . All rights reserved.
 */
class HomeItemFragment : BaseFragment() {

    companion object {
        private const val PARAMS_TAB = "params_tab"
        const val TAB_COMPONENT = 1001
        const val TAB_UTIL = 1002
        const val TAB_LAB = 1003

        fun newInstance(tab: Int): HomeItemFragment {
            val fragment = HomeItemFragment()
            val args = Bundle()
            args.putInt(PARAMS_TAB, tab)
            fragment.arguments = args
            return fragment
        }
    }

    private var mTabType = TAB_COMPONENT

    private val mDataList: MutableList<FuncItemDescription> = ArrayList()
    private lateinit var mAdapter: HomeItemAdapter

    override fun setLayoutId(): Int {
        return R.layout.fragment_home_tab
    }

    override fun initParamsAndValues() {
        mTabType = arguments?.getInt(PARAMS_TAB)!!
        if (mDataList.isNotEmpty()) {
            mDataList.clear()
            mAdapter.notifyDataSetChanged()
        }
        when (mTabType) {
            TAB_COMPONENT -> {
                val list = FuncDataManager.getInstance()?.getComponentsDescriptions()
                if (list != null && list.isNotEmpty()) {
                    mDataList.addAll(list)
                }
                tv_toolbar_title.setText(R.string.nav_compontents)
            }
            TAB_UTIL -> {
                val list = FuncDataManager.getInstance()?.getUtilsDescriptions()
                if (list != null && list.isNotEmpty()) {
                    mDataList.addAll(list)
                }
                tv_toolbar_title.setText(R.string.nav_tools)
            }
            TAB_LAB -> {
                val list = FuncDataManager.getInstance()?.getLabsDescriptions()
                if (list != null && list.isNotEmpty()) {
                    mDataList.addAll(list)
                }
                tv_toolbar_title.setText(R.string.nav_lab)
            }
        }
        mAdapter = HomeItemAdapter(dataList = mDataList)
    }

    override fun initViews(rootView: View) {
        rv_home_tab.adapter = mAdapter
        rv_home_tab.layoutManager = GridLayoutManager(mContext, 4)
        rv_home_tab.addItemDecoration(GridDividerItemDecoration(mContext, 4))

        mAdapter.setOnItemClickListener { adapter, view, position ->
            val entity = (adapter as HomeItemAdapter).getItem(position)
            val fragment = entity.demoClass.newInstance()
            if (fragment != null) {
                startFragment(fragment)
            }
        }
    }
}