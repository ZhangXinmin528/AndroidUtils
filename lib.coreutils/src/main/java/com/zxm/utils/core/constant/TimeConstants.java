package com.zxm.utils.core.constant;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/03/13
 *     desc  : The constants of time.
 * </pre>
 */
public final class TimeConstants {

    public static final int MSEC = 1;
    public static final int SEC = 1000;
    public static final int MIN = 60 * SEC;
    public static final int HOUR = 60 * MIN;
    public static final int DAY = 24 * HOUR;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
