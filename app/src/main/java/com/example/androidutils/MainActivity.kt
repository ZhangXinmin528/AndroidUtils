package com.example.androidutils

import android.os.Bundle
import com.coding.zxm.lib_core.base.BaseFragmentActivity

class MainActivity : BaseFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startFragment(HomeFragment.newInstance())
    }
}