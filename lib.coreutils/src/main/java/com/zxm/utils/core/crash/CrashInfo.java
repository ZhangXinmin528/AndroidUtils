package com.zxm.utils.core.crash;

import java.io.Serializable;

/**
 * Created by ZhangXinmin on 2019/8/20.
 * Copyright (c) 2019 . All rights reserved.
 */
public class CrashInfo implements Serializable {
    private final Throwable tr;
    private final long time;

    public CrashInfo(Throwable tr, long time) {
        this.tr = tr;
        this.time = time;
    }

    public Throwable getTr() {
        return tr;
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "CrashInfo{" +
                "tr=" + tr +
                ", time=" + time +
                '}';
    }
}
