package com.zxm.utils.core

import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by ZhangXinmin on 2022/09/01.
 * Copyright (c) 2022/9/1 . All rights reserved.
 * 扩展工具
 */

/**
 * dp to px
 */
val Float.px
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )