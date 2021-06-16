package com.example.androidutils.manager

import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.fragment.component.*
import com.example.androidutils.fragment.lab.CrashFragment
import com.example.androidutils.fragment.lab.PaletteFrament
import com.example.androidutils.fragment.lab.PingFragment
import com.example.androidutils.fragment.util.*
import com.example.androidutils.model.FuncItemDescription

/**
 * Created by ZhangXinmin on 2021/03/14.
 * Copyright (c) 3/14/21 . All rights reserved.
 */
class FuncDataManager private constructor() {

    private val mComponentsNames: MutableList<Class<out BaseFragment?>> = ArrayList()
    private val mUtilsNames: MutableList<Class<out BaseFragment?>> = ArrayList()
    private val mLabsNames: MutableList<Class<out BaseFragment?>> = ArrayList()

    init {
        initComponentsDesc()
        initLabsDesc()
        initUtilssDesc()
    }

    companion object {

        private var sInstance: FuncDataManager? = null
        private lateinit var mFunctionContainer: FunctionContainer

        fun getInstance(): FuncDataManager? {
            if (sInstance == null) {
                sInstance = FuncDataManager()
                mFunctionContainer = FunctionContainer.getInstance()
            }
            return sInstance
        }
    }

    private fun initComponentsDesc() {
        mComponentsNames.add(AppInfoFragment::class.java)
        mComponentsNames.add(StatusBarFragment::class.java)
        mComponentsNames.add(SpanFragment::class.java)
        mComponentsNames.add(DialogFragment::class.java)
        mComponentsNames.add(NetWatcherFragment::class.java)
    }

    private fun initLabsDesc() {
        mLabsNames.add(CrashFragment::class.java)
        mLabsNames.add(PaletteFrament::class.java)
        mLabsNames.add(PingFragment::class.java)
    }

    private fun initUtilssDesc() {
        mUtilsNames.add(DeviceFragment::class.java)
        mUtilsNames.add(EncryptFragment::class.java)
        mUtilsNames.add(ImageFragment::class.java)
        mUtilsNames.add(KeyboradFragment::class.java)
        mUtilsNames.add(LogFragment::class.java)
        mUtilsNames.add(NotificationFragment::class.java)
        mUtilsNames.add(PermissionFragment::class.java)
        mUtilsNames.add(ScreenFragment::class.java)
    }

    fun getDescription(clz: Class<out BaseFragment>): FuncItemDescription {
        return mFunctionContainer.getFragment(clz)
    }

    fun getDescriptionName(clz: Class<out BaseFragment>): String {
        return getDescription(clz).name
    }

    fun getComponentsDescriptions(): MutableList<FuncItemDescription> {
        val list = ArrayList<FuncItemDescription>()
        mComponentsNames.forEach {
            list.add(mFunctionContainer.getFragment(it))
        }
        return list
    }

    fun getUtilsDescriptions(): MutableList<FuncItemDescription> {
        val list = ArrayList<FuncItemDescription>()
        mUtilsNames.forEach {
            list.add(mFunctionContainer.getFragment(it))
        }
        return list
    }

    fun getLabsDescriptions(): MutableList<FuncItemDescription> {
        val list = ArrayList<FuncItemDescription>()
        mLabsNames.forEach {
            list.add(mFunctionContainer.getFragment(it))
        }
        return list
    }
}