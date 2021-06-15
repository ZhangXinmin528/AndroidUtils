package com.coding.zxm.lib_core.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
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
    protected val sTAG = this.javaClass.simpleName

    override fun getContainerViewId(): Int {
        return R.id.activity_fragment_container_id
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRootView = RootView(context = this, containerId = getContainerViewId())
        mRootView?.let {
            setContentView(it)
        }
    }

    private fun getCurrentFragment(): Fragment? {
        return getContainerFragmentManager().findFragmentById(getContainerViewId())
    }

    fun getCurrentTypeFragment(): BaseFragment? {
        val fragment = getCurrentFragment()
        if (fragment != null && fragment is BaseFragment) {
            return fragment
        }
        return null
    }

    /**
     * Start the target fragment.
     */
    fun startFragment(fragment: BaseFragment): Int {
        Log.d(sTAG, "startFragment()~¬")
        if (supportFragmentManager.isStateSaved) {
            Log.e(sTAG, "startFragment can not be invoked after onSaveInstanceState")
            return -1
        }

        val transitionConfig = fragment.onFetchTransitionConfig()
        val tagName = fragment::class.simpleName
        return getContainerFragmentManager().beginTransaction()
            .setCustomAnimations(
                transitionConfig.enter,
                transitionConfig.exit,
                transitionConfig.popEnter,
                transitionConfig.popExit
            )
            .replace(getContainerViewId(), fragment, tagName)
            .setPrimaryNavigationFragment(null)
            .addToBackStack(tagName)
            .commit()
    }

    //=================================provider=============================================//
    override fun getContainerFragmentManager(): FragmentManager {
        return supportFragmentManager
    }

    override fun getContainerViewModelStoreOwner(): ViewModelStoreOwner? {
        return this
    }

    override fun getFragmentContainerView(): FragmentContainerView? {
        return mRootView?.getFragmentContainerView()
    }

    override fun requestForHandlePopBack(toHandle: Boolean) {
        isChildHandlePopBackRequested = toHandle
    }

    override fun isChildHandlePopBackRequested(): Boolean {
        return isChildHandlePopBackRequested
    }

    //=====================================back press======================================//

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val fragment = getCurrentTypeFragment()
        if (fragment != null && fragment.onKeyDown(keyCode, event)) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        val fragment = getCurrentTypeFragment()
        if (fragment != null && fragment.onKeyUp(keyCode, event)) {
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

    fun popBackStaCK() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onBackPressed() {
        try {
            super.onBackPressed()
        } catch (exception: Exception) {
            // 1. Under Android O, Activity#onBackPressed doesn't check FragmentManager's save state.
            // 2. IndexOutOfBoundsException caused by ViewGroup#removeView(View) in EmotionUI.
        }

    }

    //===================================================================================//

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