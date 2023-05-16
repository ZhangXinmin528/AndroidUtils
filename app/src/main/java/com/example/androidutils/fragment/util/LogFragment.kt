package com.example.androidutils.fragment.util

import android.annotation.SuppressLint
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentLogBinding
import com.zxm.utils.core.log.MLogger
import com.zxm.utils.core.log.MLogger.LogConfig

/**
 * Created by ZhangXinmin on 2019/3/19.
 * Copyright (c) 2018 . All rights reserved.
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.UTILS, funcName = "日志工具", funcIconRes = R.mipmap.icon_log_info)
class LogFragment : BaseFragment(), View.OnClickListener {

    private lateinit var logBinding: FragmentLogBinding

    companion object {
        private val TAG = LogFragment::class.java.simpleName
        private const val UPDATE_LOG = 0x01
        private const val UPDATE_CONSOLE = 0x01 shl 1
        private const val UPDATE_TAG = 0x01 shl 2
        private const val UPDATE_HEAD = 0x01 shl 3
        private const val UPDATE_FILE = 0x01 shl 4
        private const val UPDATE_DIR = 0x01 shl 5
        private const val UPDATE_BORDER = 0x01 shl 6
        private const val UPDATE_SINGLE = 0x01 shl 7
        private const val UPDATE_CONSOLE_FILTER = 0x01 shl 8
        private const val UPDATE_FILE_FILTER = 0x01 shl 9
        private var longStr: String? = null

        init {
            val sb = StringBuilder()
            sb.append("len = 10400\ncontent = \"")
            for (i in 0..799) {
                sb.append("Hello world. ")
            }
            sb.append("\"")
            longStr = sb.toString()
        }
    }

    private var dir: String? = ""
    private var globalTag = ""
    private var log = true
    private var console = true
    private var head = true
    private var file = false
    private var border = true
    private var single = true
    private var consoleFilter = MLogger.V
    private var fileFilter = MLogger.V
    private val builder: LogConfig = MLogger.getLogConfig()
    private val mRunnable = Runnable {
        MLogger.v("verbose")
        MLogger.d("debug")
        MLogger.i("info")
        MLogger.w("warn")
        MLogger.e("error")
        MLogger.a("assert")
    }

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        logBinding = FragmentLogBinding.inflate(inflater, container, false)
        return logBinding.root
    }

    override fun initParamsAndValues() {
        initViews()
    }

    fun initViews() {
        logBinding.layoutTitle.tvToolbarTitle.text = "日志工具"
        logBinding.layoutTitle.ivToolbarBack.setOnClickListener(this)

        //默认设置
        logBinding.tvAboutLog.setText(builder.toString())
        logBinding.btnToggleLog.setOnClickListener(this)
        logBinding.btnToggleConsole.setOnClickListener(this)
        logBinding.btnToggleTag.setOnClickListener(this)
        logBinding.btnToggleHead.setOnClickListener(this)
        logBinding.btnToggleBorder.setOnClickListener(this)
        logBinding.btnToggleSingle.setOnClickListener(this)
        logBinding.btnToggleFile.setOnClickListener(this)
        logBinding.btnToggleDir.setOnClickListener(this)
        logBinding.btnToggleConoleFilter.setOnClickListener(this)
        logBinding.btnToggleFileFilter.setOnClickListener(this)
        logBinding.btnLogNoTag.setOnClickListener(this)
        logBinding.btnLogWithTag.setOnClickListener(this)
        logBinding.btnLogInNewThread.setOnClickListener(this)
        logBinding.btnLogNull.setOnClickListener(this)
        logBinding.btnLogManyParams.setOnClickListener(this)
        logBinding.btnLogLong.setOnClickListener(this)
        logBinding.btnLogFile.setOnClickListener(this)
        logBinding.btnLogJson.setOnClickListener(this)
        logBinding.btnLogXml.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }
            R.id.btn_toggle_log -> updateConfig(UPDATE_LOG)
            R.id.btn_toggle_console -> updateConfig(UPDATE_CONSOLE)
            R.id.btn_toggle_tag -> updateConfig(UPDATE_TAG)
            R.id.btn_toggle_head -> updateConfig(UPDATE_HEAD)
            R.id.btn_toggle_file -> updateConfig(UPDATE_FILE)
            R.id.btn_toggle_dir -> updateConfig(UPDATE_DIR)
            R.id.btn_toggle_border -> updateConfig(UPDATE_BORDER)
            R.id.btn_toggle_single -> updateConfig(UPDATE_SINGLE)
            R.id.btn_toggle_conole_filter -> updateConfig(UPDATE_CONSOLE_FILTER)
            R.id.btn_toggle_file_filter -> updateConfig(UPDATE_FILE_FILTER)
            R.id.btn_log_no_tag -> {
                MLogger.v("verbose")
                MLogger.d("debug")
                MLogger.i("info")
                MLogger.w("warn")
                MLogger.e("error")
                MLogger.a("assert")
            }
            R.id.btn_log_with_tag -> {
                MLogger.vTag("customTag", "verbose")
                MLogger.dTag("customTag", "debug")
                MLogger.iTag("customTag", "info")
                MLogger.wTag("customTag", "warn")
                MLogger.eTag("customTag", "error")
                MLogger.aTag("customTag", "assert")
            }
            R.id.btn_log_in_new_thread -> {
                val thread = Thread(mRunnable)
                thread.start()
            }
            R.id.btn_log_null -> {
                MLogger.v(null as Any?)
                MLogger.d(null as Any?)
                MLogger.i(null as Any?)
                MLogger.w(null as Any?)
                MLogger.e(null as Any?)
                MLogger.a(null as Any?)
            }
            R.id.btn_log_many_params -> {
                MLogger.v("verbose0", "verbose1")
                MLogger.vTag("customTag", "verbose0", "verbose1")
                MLogger.d("debug0", "debug1")
                MLogger.dTag("customTag", "debug0", "debug1")
                MLogger.i("info0", "info1")
                MLogger.iTag("customTag", "info0", "info1")
                MLogger.w("warn0", "warn1")
                MLogger.wTag("customTag", "warn0", "warn1")
                MLogger.e("error0", "error1")
                MLogger.eTag("customTag", "error0", "error1")
                MLogger.a("assert0", "assert1")
                MLogger.aTag("customTag", "assert0", "assert1")
            }
            R.id.btn_log_long -> MLogger.d(longStr)
            R.id.btn_log_file -> {
                var i = 0
                while (i < 15) {
                    MLogger.file("test0 log to file")
                    i++
                }
            }
            R.id.btn_log_json -> {
                val json =
                    "{\"tools\": [{ \"name\":\"css format\" , \"site\":\"http://tools.w3cschool.cn/code/css\" },{ \"name\":\"json format\" , \"site\":\"http://tools.w3cschool.cn/code/json\" },{ \"name\":\"pwd check\" , \"site\":\"http://tools.w3cschool.cn/password/my_password_safe\" }]}"
                MLogger.json(json)
            }
            R.id.btn_log_xml -> {
                val xml =
                    "<books><book><author>Jack Herrington</author><title>PHP Hacks</title><publisher>O'Reilly</publisher></book><book><author>Jack Herrington</author><title>Podcasting Hacks</title><publisher>O'Reilly</publisher></book></books>"
                MLogger.xml(xml)
            }
        }
    }

    private fun updateConfig(args: Int) {
        when (args) {
            UPDATE_LOG -> log = !log
            UPDATE_CONSOLE -> console = !console
            UPDATE_TAG -> globalTag = if (globalTag == Companion.TAG) "" else Companion.TAG
            UPDATE_HEAD -> head = !head
            UPDATE_FILE -> file = !file
            UPDATE_DIR -> if (getDir().contains("test")) {
                dir = null
            } else {
                if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
                    dir =
                        Environment.getExternalStorageDirectory().path + System.getProperty("file.separator") + "test"
                }
            }
            UPDATE_BORDER -> border = !border
            UPDATE_SINGLE -> single = !single
            UPDATE_CONSOLE_FILTER -> consoleFilter =
                if (consoleFilter == MLogger.V) MLogger.W else MLogger.V
            UPDATE_FILE_FILTER -> fileFilter = if (fileFilter == MLogger.V) MLogger.I else MLogger.V
        }
        builder!!.setLogSwitch(log)
            .setConsoleSwitch(console)
            .setGlobalTag(globalTag)
            .setLogHeadSwitch(head)
            .setLog2FileSwitch(file)
            .setDir(dir)
            .setBorderSwitch(border)
            .setSingleTagSwitch(single)
            .setConsoleFilter(consoleFilter)
            .setFileFilter(fileFilter)
        logBinding.tvAboutLog.setText(builder.toString())
    }

    private fun getDir(): String {
        return builder.toString().split(System.getProperty("line.separator")!!.toRegex())
            .toTypedArray()[5].substring(5)
    }
}