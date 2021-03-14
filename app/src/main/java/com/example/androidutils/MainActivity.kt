package com.example.androidutils

import com.example.androidutils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initParamsAndValues() {
        vp_home.adapter

    }

    override fun initViews() {

    }

}