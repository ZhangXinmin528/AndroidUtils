package com.zxm.utils.core.net;

import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.zxm.utils.core.ShellUtils;

/**
 * Created by ZhangXinmin on 2019/5/17.
 * Copyright (c) 2018 . All rights reserved.
 */
public class PingUtil {

    private static final String TYPE_SPACE = " ";

    /**
     * Stop ping after a specified number of times.
     */
    private static final String COMMAND_COUNT = "-c";

    /**
     * Time interval to send ping package,default is 1 second.
     */
    private static final String COMMAND_INTERVAL = "-i";

    private PingUtil() {
        throw new UnsupportedOperationException("U con't do this!");
    }

    /**
     * Check ping.
     *
     * @param count     A specified number of times to stop ping.
     * @param interval  Time interval to send ping package,default is 1 second.
     * @param ip        Ip address.
     * @param isRoot    Mobile device root state.
     * @return result.
     */
    public static ShellUtils.CommandResult ping(@IntRange(from = 1) int count,
                                                @FloatRange(from = 0.1) float interval,
                                                @NonNull String ip,
                                                boolean isRoot) {
        return ping(count, interval, ip, isRoot, true);

    }

    /**
     * Check ping.
     *
     * @param count     A specified number of times to stop ping.
     * @param interval  Time interval to send ping package,default is 1 second.
     * @param ip        Ip address.
     * @param isRoot    Mobile device root state.
     * @param isNeedMsg Wheather the result is needed.
     * @return result.
     */
    public static ShellUtils.CommandResult ping(@IntRange(from = 1) int count,
                                                @FloatRange(from = 0.1) float interval,
                                                @NonNull String ip,
                                                boolean isRoot, boolean isNeedMsg) {
        if (!TextUtils.isEmpty(ip)) {
            final StringBuffer sb = new StringBuffer();
            sb.append("ping");
            sb.append(TYPE_SPACE);
            if (count >= 1) {
                sb.append(COMMAND_COUNT).append(TYPE_SPACE).append(count).append(TYPE_SPACE);
            }
            if (interval >= 0.1) {
                sb.append(COMMAND_INTERVAL).append(TYPE_SPACE).append(interval).append(TYPE_SPACE);
            }
            sb.append(ip);

            final String command = sb.toString();
            return ShellUtils.execCmd(command, isRoot, isNeedMsg);
        }
        return null;
    }
}
