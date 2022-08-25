package com.zxm.utils.core.widget.loginfo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.PixelFormat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zxm.utils.core.R
import com.zxm.utils.core.file.FileIOUtils
import com.zxm.utils.core.loginfo.LogInfoManager
import com.zxm.utils.core.loginfo.LogLine
import com.zxm.utils.core.time.TimeUtil
import java.io.File
import java.util.concurrent.Executors

/**
 * Created by ZhangXinmin on 2022/08/24.
 * Copyright (c) 2022/8/24 . All rights reserved.
 * 日志信息面板
 */
internal class LogInfoPanel : LogInfoManager.OnLogCatchListener, View.OnClickListener,
    View.OnAttachStateChangeListener {

    private val sTag: String = "LogInfoPannel"
    private lateinit var mContext: Context
    private lateinit var mRootView: View
    private var mWindowManager: WindowManager? = null
    private var isAttachedToWindow: Boolean = false

    /**
     * 单行的log
     */
    private var mLogHint: TextView? = null
    private var mLogRvWrap: RelativeLayout? = null

    private var mLogRv: RecyclerView? = null
    private var mLogItemAdapter: LogItemAdapter? = null
    private var mLogFilter: EditText? = null
    private var mRadioGroup: RadioGroup? = null

    private var mAutoScrollToBottom = true
    private var counter = 0
    private var mIsLoaded = false
    private val mExecutorService = Executors.newSingleThreadExecutor()

    companion object {

        private const val MAX_LOG_LINE_NUM = 10000
        private const val UPDATE_CHECK_INTERVAL = 200
    }

    constructor(context: Context) {
        initParamsAndViews(context)
        LogInfoManager.getInstance().start()
    }

    private fun initParamsAndViews(context: Context) {
        Log.e(sTag, "initParamsAndViews()")
        mContext = context
        mRootView = LayoutInflater.from(context).inflate(R.layout.layout_log_info_panel, null)

        mWindowManager = if (context is Activity) {
            context.windowManager
        } else {
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }

        if (mWindowManager == null) {
            Log.e(sTag, "WindowManager is not exit!")
            return
        }

        mLogHint = mRootView.findViewById(R.id.log_hint)
        mLogRvWrap = mRootView.findViewById(R.id.log_page)
        mLogRv = mRootView.findViewById(R.id.log_list)
        mLogRv?.layoutManager = LinearLayoutManager(mContext)
        mLogItemAdapter = LogItemAdapter(mContext)
        mLogRv?.adapter = mLogItemAdapter
        mLogFilter = mRootView.findViewById(R.id.log_filter)
        mLogFilter?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                mLogItemAdapter?.filter?.filter(s)
            }

        })

        val logTitleBar = mRootView.findViewById<LogTitleBar>(R.id.dokit_title_bar)
        logTitleBar.setListener(object : LogTitleBar.OnTitleBarClickListener {
            override fun onRightClick() {
                closePanel()
            }

            override fun onLeftClick() {
                minimize()
            }

        })

        mLogHint?.setOnClickListener {
            //最大化窗口
            maximize()
        }

        mRootView.setOnClickListener {
//            Toast.makeText(mContext, "点击了面板", Toast.LENGTH_SHORT).show()
        }
        mRadioGroup = mRootView.findViewById(R.id.radio_group)
        mRadioGroup?.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.verbose -> {
                    mLogItemAdapter?.logLevelLimit = Log.VERBOSE
                }
                R.id.debug -> {
                    mLogItemAdapter?.logLevelLimit = Log.DEBUG
                }
                R.id.info -> {
                    mLogItemAdapter?.logLevelLimit = Log.INFO
                }
                R.id.warn -> {
                    mLogItemAdapter?.logLevelLimit = Log.WARN
                }
                R.id.error -> {
                    mLogItemAdapter?.logLevelLimit = Log.ERROR
                }
            }
            mLogItemAdapter?.filter?.filter(mLogFilter?.text)
        }

        mLogRv?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager: LinearLayoutManager =
                    recyclerView.layoutManager as LinearLayoutManager
                mAutoScrollToBottom =
                    (layoutManager.findLastCompletelyVisibleItemPosition() == recyclerView.adapter!!.itemCount - 1)
            }
        })

        mRadioGroup?.check(R.id.verbose)
        mRootView.findViewById<Button>(R.id.btn_top).setOnClickListener(this)
        mRootView.findViewById<Button>(R.id.btn_bottom).setOnClickListener(this)
        mRootView.findViewById<Button>(R.id.btn_clean).setOnClickListener(this)
        mRootView.findViewById<Button>(R.id.btn_export).setOnClickListener(this)

        LogInfoManager.getInstance().registerListener(this)


        mRootView.addOnAttachStateChangeListener(this)
    }

    internal fun attach() {
        Log.d(sTag, "attach()..isAttachedToWindow:$isAttachedToWindow)")
        if (!isAttachedToWindow) {
            mLogHint?.visibility = View.GONE
            mLogRvWrap?.visibility = View.VISIBLE

            val layoutParams = WindowManager.LayoutParams()
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL

            //The desired bitmap format
            layoutParams.format = PixelFormat.RGBA_8888
            mWindowManager?.addView(mRootView, layoutParams)
        } else {
            Toast.makeText(mContext, "已经添加过了", Toast.LENGTH_SHORT).show()
            maximize()
        }
    }

    /**
     * 是否最大化
     */
    private var isMaximize = true
    internal fun minimize() {
        isMaximize = false
        mLogHint?.visibility = View.VISIBLE
        mLogRvWrap?.visibility = View.GONE

        val layoutParams =
            mRootView.layoutParams as WindowManager.LayoutParams?
                ?: return

        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        //The desired bitmap format
        layoutParams.format = PixelFormat.RGBA_8888

        layoutParams.gravity = Gravity.TOP or Gravity.START
        mWindowManager?.updateViewLayout(mRootView, layoutParams)

    }

    internal fun maximize() {
        isMaximize = false
        mLogHint?.visibility = View.GONE
        mLogRvWrap?.visibility = View.VISIBLE

        val layoutParams =
            mRootView.layoutParams as WindowManager.LayoutParams?
                ?: return

        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        //The desired bitmap format
        layoutParams.format = PixelFormat.RGBA_8888

        layoutParams.gravity = Gravity.TOP or Gravity.START
        mWindowManager?.updateViewLayout(mRootView, layoutParams)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onLogCatch(logLines: MutableList<LogLine>) {
        if (mLogRv == null || mLogItemAdapter == null) {
            return
        }

        if (!mIsLoaded) {
            mIsLoaded = true
            mRootView.findViewById<View>(R.id.ll_loading).visibility = View.GONE
            mLogRv?.visibility = View.VISIBLE
        }

        if (logLines.size == 1) {
            mLogItemAdapter?.addWithFilter(logLines.get(0), mLogFilter?.text, true)

        } else {
            logLines.forEach {
                mLogItemAdapter?.addWithFilter(it, mLogFilter?.text, false)
            }
            mLogItemAdapter?.notifyDataSetChanged()
        }

        if (logLines.size > 0) {
            val line = logLines.get(logLines.size - 1)
            mLogHint?.text = "${line.tag}:${line.logOutput}"

        }
        if (++counter % UPDATE_CHECK_INTERVAL == 0
            && mLogItemAdapter!!.trueValues.size > MAX_LOG_LINE_NUM
        ) {
            val numItemToRemove = mLogItemAdapter!!.trueValues.size - MAX_LOG_LINE_NUM
            mLogItemAdapter?.removeFirst(numItemToRemove)
        }

        if (mAutoScrollToBottom) {
            mLogRv?.scrollToPosition(mLogItemAdapter!!.itemCount - 1)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_top -> {
                if (mLogItemAdapter == null || mLogItemAdapter?.itemCount == 0) {
                    return
                }
                mLogRv?.smoothScrollToPosition(0)
            }
            R.id.btn_bottom -> {
                if (mLogItemAdapter == null || mLogItemAdapter?.itemCount == 0) {
                    return
                }
                mLogRv?.smoothScrollToPosition(mLogItemAdapter!!.itemCount - 1)
            }
            R.id.btn_export -> {
                if (mLogItemAdapter == null || mLogItemAdapter?.itemCount == 0) {
                    Toast.makeText(mContext, "暂无日志信息可以导出", Toast.LENGTH_SHORT).show()
                    return
                }

                //todo:保存 or 分享
                exportFile()
            }
            R.id.btn_clean -> {
                if (mLogItemAdapter == null || mLogItemAdapter?.itemCount == 0) {
                    return
                }
                counter = 0
                mLogItemAdapter!!.clearLog()
            }
        }
    }

    private fun exportFile() {
        val filePath =
            mContext.filesDir.absolutePath + File.separator + "logInfo" + File.separator + TimeUtil.getNowString() + ".log"

        val file = File(filePath)
        mExecutorService.execute {
            try {
                mLogItemAdapter?.let {
                    val logLines = ArrayList<LogLine>(it.trueValues)
                    logLines.forEach { line ->
                        val logContent: String = line.processId
                            .toString() + "   " + "   " + line.timestamp + "   " + line.tag +
                                "   " + line.logLevelText + "   " + line.logOutput + "\n"
                        FileIOUtils.writeFileFromString(file, logContent, true)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (file.exists()) {
                    Log.d(sTag, "日志文件保存在：$filePath")
                } else {
                    Log.e(sTag, "日志文件保存失败")
                }
            }
        }
    }


    /**
     * 关闭面板
     */
    internal fun closePanel() {
        Log.d(sTag, "closePanel()")
        try {
            //关闭日志服务
            LogInfoManager.getInstance().stop()
            //清空回调
            LogInfoManager.getInstance().removeListener()

            if (isAttachedToWindow) {
                mWindowManager?.removeViewImmediate(mRootView)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onViewAttachedToWindow(v: View?) {
        isAttachedToWindow = true
        Log.d(sTag, "mRootView has attached to window!")
    }

    override fun onViewDetachedFromWindow(v: View?) {
        isAttachedToWindow = false
        Log.d(sTag, "mRootView has detached from window!")
    }

}