package com.coding.zxm.lib_core.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelStoreOwner
import com.coding.zxm.lib_core.R

/**
 * Created by ZhangXinmin on 2020/7/19.
 * Copyright (c) 2020 . All rights reserved.
 * Base activity~
 */
abstract class BaseFragmentActivity : AppCompatActivity(), FragmentContainerProvider {

    private var isChildHandlePopBackRequested = false
    private var mRootView: RootView? = null

    protected fun getContainerViewId(): Int {
        return R.id.activity_fragment_container_id
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRootView = RootView(context = this, containerId = getContainerViewId())
        mRootView?.let {
            setContentView(it)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyUp(keyCode, event)
    }

    fun getCurrentFragment(): Fragment? {
        return getContainerFragmentManager().findFragmentById(getContainerViewId())
    }

    override fun getContainerFragmentManager(): FragmentManager {
        return supportFragmentManager
    }

    override fun getContainerViewModelStoreOwner(): ViewModelStoreOwner? {
        return this
    }

    override fun requestForHandlePopBack(toHandle: Boolean) {
        isChildHandlePopBackRequested = toHandle
    }

    override fun isChildHandlePopBackRequested(): Boolean {
        return isChildHandlePopBackRequested
    }

    @SuppressLint("ViewConstructor")
    class RootView : FrameLayout {

        private val mFragmentContainerView: FragmentContainerView = FragmentContainerView(context)

        constructor(context: Context, containerId: Int) : super(context) {
            mFragmentContainerView.id = containerId

            addView(
                mFragmentContainerView,
                LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }

        fun getFragmentContainerView(): FragmentContainerView {
            return mFragmentContainerView
        }
    }
}