package com.example.androidutils

import android.os.Bundle
import com.coding.zxm.lib_core.base.BaseFragmentActivity

class MainActivity : BaseFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val homeFragment = HomeFragment.newInstance()
//        val tagName = homeFragment.javaClass.simpleName
//        supportFragmentManager.beginTransaction()
//            .add(getContainerViewId(), homeFragment, tagName)
//            .addToBackStack(tagName)
//            .commit()

        startFragment(HomeFragment.newInstance())
    }

}