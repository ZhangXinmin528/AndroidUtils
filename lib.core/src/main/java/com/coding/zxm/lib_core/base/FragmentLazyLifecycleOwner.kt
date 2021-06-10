package com.coding.zxm.lib_core.base

import androidx.lifecycle.*

/**
 * Created by ZhangXinmin on 2021/06/10.
 * Copyright (c) 2021/6/10 . All rights reserved.
 */
class FragmentLazyLifecycleOwner(private val mCallback: Callback) : LifecycleObserver,
    LifecycleOwner {

    private var mLifecycleRegistry: LifecycleRegistry? = null
    private var mIsViewVisible = true
    private var mViewState = Lifecycle.State.INITIALIZED

    private fun initialize() {
        if (mLifecycleRegistry == null) {
            mLifecycleRegistry = LifecycleRegistry(this)
        }
    }

    fun setViewVisible(viewVisible: Boolean) {
        if (mViewState < Lifecycle.State.CREATED || !isInitialized()) {
            // not trust it before onCreate
            return
        }

        mIsViewVisible = viewVisible

        if (viewVisible) {
            mLifecycleRegistry?.markState(mViewState)
        } else {
            if (mViewState > Lifecycle.State.CREATED) {
                mLifecycleRegistry?.markState(Lifecycle.State.CREATED)
            } else {
                mLifecycleRegistry?.markState(mViewState)
            }
        }
    }

    /**
     * @return True if the Lifecycle has been initialized.
     */
    fun isInitialized(): Boolean {
        return mLifecycleRegistry != null
    }

    override fun getLifecycle(): Lifecycle {
        initialize()
        return mLifecycleRegistry!!
    }

    private fun handleLifecycleEvent(event: Lifecycle.Event) {
        initialize()
        mLifecycleRegistry?.handleLifecycleEvent(event)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner?) {
        mIsViewVisible = mCallback.isVisibleToUser()
        mViewState = Lifecycle.State.CREATED
        handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(owner: LifecycleOwner?) {
        mViewState = Lifecycle.State.STARTED
        if (mIsViewVisible) {
            handleLifecycleEvent(Lifecycle.Event.ON_START)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(owner: LifecycleOwner?) {
        mViewState = Lifecycle.State.RESUMED
        if (mIsViewVisible && mLifecycleRegistry!!.currentState == Lifecycle.State.STARTED) {
            handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(owner: LifecycleOwner?) {
        mViewState = Lifecycle.State.STARTED
        if (mLifecycleRegistry!!.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(owner: LifecycleOwner?) {
        mViewState = Lifecycle.State.CREATED
        if (mLifecycleRegistry!!.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner?) {
        mViewState = Lifecycle.State.DESTROYED
        handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    interface Callback {
        fun isVisibleToUser(): Boolean
    }

}
