package com.example.androidutils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.adapter.HomeTabAdapter
import com.example.androidutils.databinding.FragmentHomeBinding
import com.example.androidutils.fragment.HomeItemFragment

/**
 * Created by ZhangXinmin on 2021/06/15.
 * Copyright (c) 2021/6/15 . All rights reserved.
 */
class HomeFragment : BaseFragment() {

    private val mFragments: MutableList<Fragment> = ArrayList()
    private lateinit var homeBinding: FragmentHomeBinding

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding.root
    }

    override fun initParamsAndValues() {
        //TODO:什么原因导致生命周期重新执行？？
        mFragments.clear()
        mFragments.add(HomeItemFragment.newInstance(HomeItemFragment.TAB_COMPONENT))
        mFragments.add(HomeItemFragment.newInstance(HomeItemFragment.TAB_UTIL))
        mFragments.add(HomeItemFragment.newInstance(HomeItemFragment.TAB_TV))
        mFragments.add(HomeItemFragment.newInstance(HomeItemFragment.TAB_LAB))

        initViews()
    }

    fun initViews() {
        homeBinding.vpHome.adapter =
            HomeTabAdapter(fragmentManager = childFragmentManager, dataList = mFragments)
        homeBinding.vpHome.offscreenPageLimit = 2

        homeBinding.vpHome.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                homeBinding.navMain.selectedItemId = position
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        homeBinding.navMain.setupWithViewPager(homeBinding.vpHome)
        homeBinding.navMain.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_component -> {
                    homeBinding.vpHome.currentItem = 0
                    true
                }
                R.id.nav_util -> {
                    homeBinding.vpHome.currentItem = 1
                    true
                }
                R.id.nav_tv -> {
                    homeBinding.vpHome.currentItem = 2
                    true
                }
                R.id.nav_lab -> {
                    homeBinding.vpHome.currentItem = 3
                    true
                }
                else -> {
                    false
                }
            }

        }
    }
}