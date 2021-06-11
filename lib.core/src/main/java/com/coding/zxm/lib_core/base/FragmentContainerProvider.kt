package com.coding.zxm.lib_core.base

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelStoreOwner

/**
 * Created by ZhangXinmin on 2021/06/11.
 * Copyright (c) 2021/6/11 . All rights reserved.
 */
interface FragmentContainerProvider {

    fun getContainerFragmentManager(): FragmentManager

    fun getContainerViewModelStoreOwner(): ViewModelStoreOwner?

    fun requestForHandlePopBack(toHandle: Boolean)

    fun isChildHandlePopBackRequested(): Boolean
}