package com.zxm.utils.core.widget.loginfo

import android.content.Context
import com.zxm.utils.core.log.MLogger

/**
 * Created by ZhangXinmin on 2022/08/25.
 * Copyright (c) 2022/8/25 . All rights reserved.
 */
class LogInfoKit private constructor() {

    private val sTAG = "LogInfoKit"
    private var logInfoPanel: LogInfoPanel? = null

    companion object {
        private var sINSTANCE: LogInfoKit? = null

        fun getInstance(): LogInfoKit {
            if (sINSTANCE == null) {
                sINSTANCE = LogInfoKit()
            }
            return sINSTANCE!!
        }
    }

    /**
     * 展示日志信息面板
     * @param context
     */
    fun onAttach(context: Context) {
        MLogger.d(sTAG, "onAttach..${logInfoPanel == null}")
        if (logInfoPanel == null) {
            logInfoPanel = LogInfoPanel(context)
        }
        logInfoPanel?.attach()
    }

    /**
     * 移除日志信息展板
     */
    fun onDetach() {
        MLogger.d(sTAG, "onDetach..${logInfoPanel == null}")
        if (logInfoPanel != null) {
            logInfoPanel?.closePanel()
//            logInfoPanel == null
        }
    }

    /**
     * 最小化日志信息展板
     */
    fun minPannel() {
        if (logInfoPanel != null) {
            logInfoPanel?.minimize()
        }
    }

    /**
     * 最大化日志信息展板
     */
    fun maxPanel() {
        if (logInfoPanel != null) {
            logInfoPanel?.maximize()
        }
    }
}