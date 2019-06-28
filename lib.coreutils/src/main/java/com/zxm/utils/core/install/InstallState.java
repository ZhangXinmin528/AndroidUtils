package com.zxm.utils.core.install;

/**
 * Created by ZhangXinmin on 2018/8/24.
 * Copyright (c) 2018 . All rights reserved.
 */
public class InstallState {
    private int type;
    private String message;

    public InstallState() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "InstallState{" +
                "type=" + type +
                ", message=" + message +
                '}';
    }
}
