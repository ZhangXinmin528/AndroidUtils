package com.example.androidutils

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.coding.zxm.lib_core.base.BaseActivity
import com.example.androidutils.adapter.HomeTabAdapter
import com.example.androidutils.fragment.HomeItemFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val mFragments: MutableList<Fragment> = ArrayList()

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initParamsAndValues() {
        setStatusBarColorWhite()
        mFragments.add(HomeItemFragment.newInstance(HomeItemFragment.TAB_COMPONENT))
        mFragments.add(HomeItemFragment.newInstance(HomeItemFragment.TAB_UTIL))
        mFragments.add(HomeItemFragment.newInstance(HomeItemFragment.TAB_LAB))

    }

    override fun initViews() {
        vp_home.adapter =
            HomeTabAdapter(fragmentManager = supportFragmentManager, dataList = mFragments)
        vp_home.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
            }

        })
    }

}