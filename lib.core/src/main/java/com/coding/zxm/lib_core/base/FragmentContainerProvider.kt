package com.coding.zxm.lib_core.base

import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelStoreOwner

/**
 * Created by ZhangXinmin on 2021/06/11.
 * Copyright (c) 2021/6/11 . All rights reserved.
 */
interface FragmentContainerProvider {
    fun getContainerViewId(): Int

    fun getFragmentContainerView(): FragmentContainerView?

    fun getContainerFragmentManager(): FragmentManager

    fun getContainerViewModelStoreOwner(): ViewModelStoreOwner?

    fun requestForHandlePopBack(toHandle: Boolean)

    fun isChildHandlePopBackRequested(): Boolean
}