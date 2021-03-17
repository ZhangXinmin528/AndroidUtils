package com.example.androidutils.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Created by ZhangXinmin on 2021/03/17.
 * Copyright (c) 3/17/21 . All rights reserved.
 */
class HomeTabAdapter(fragmentManager: FragmentManager, val dataList: MutableList<Fragment>) :
        FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return dataList.get(position)
    }

    override fun getCount(): Int {
        return dataList.size
    }
}