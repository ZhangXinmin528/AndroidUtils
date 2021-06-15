package com.example.androidutils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.adapter.HomeTabAdapter
import com.example.androidutils.fragment.HomeItemFragment
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by ZhangXinmin on 2021/06/15.
 * Copyright (c) 2021/6/15 . All rights reserved.
 */
class HomeFragment : BaseFragment() {

    private val mFragments: MutableList<Fragment> = ArrayList()

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initParamsAndValues() {
        mFragments.add(HomeItemFragment.newInstance(HomeItemFragment.TAB_COMPONENT))
        mFragments.add(HomeItemFragment.newInstance(HomeItemFragment.TAB_UTIL))
        mFragments.add(HomeItemFragment.newInstance(HomeItemFragment.TAB_LAB))
    }

    override fun initViews(rootView: View) {
        vp_home.adapter =
            HomeTabAdapter(fragmentManager = childFragmentManager, dataList = mFragments)
        vp_home.offscreenPageLimit = 2

        vp_home.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                nav_main.selectedItemId = position
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        nav_main.setupWithViewPager(vp_home)
        nav_main.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_component -> {
                    vp_home.currentItem = 0
                    true
                }
                R.id.nav_util -> {
                    vp_home.currentItem = 1
                    true
                }
                R.id.nav_lab -> {
                    vp_home.currentItem = 2
                    true
                }
                else -> {
                    false
                }
            }

        }
    }
}