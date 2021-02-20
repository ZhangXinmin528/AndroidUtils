package com.zxm.utils.core.display

import android.text.TextUtils

/**
 * Created by ZhangXinmin on 2021/02/20.
 * Copyright (c) 2/20/21 . All rights reserved.
 */
class DisplayHelper {
    companion object {
        /**
         * Whether or not the charSequence is empty?
         */
        fun isEmpty(value: CharSequence): CharSequence {
            return if (!TextUtils.isEmpty(value)) {
                value
            } else {
                ""
            }
        }
    }
}