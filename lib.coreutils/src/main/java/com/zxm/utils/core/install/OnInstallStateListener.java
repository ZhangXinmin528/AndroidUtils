package com.zxm.utils.core.install;

/**
 * Created by ZhangXinmin on 2018/8/24.
 * Copyright (c) 2018 . All rights reserved.
 */
public interface OnInstallStateListener {
    /**
     * 开始安装
     */
    void onInstallStart(String filePath);

    /**
     * 安装完成
     */
    void onInstallComplete();

    /**
     * 安装失败
     */
    void onInstallFailed(InstallState state);

    /**
     * 设置辅助功能状态
     */
    void onAccessibilitySwitchChanged();
}
