package com.example.androidutils.bean;

/**
 * Created by ZhangXinmin on 2019/6/17.
 * Copyright (c) 2018 . All rights reserved.
 */
public class NaviEntity {
    private int iconId;
    private String name;

    public NaviEntity() {
    }

    public NaviEntity(int iconId, String name) {
        this.iconId = iconId;
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NaviEntity{" +
                "iconId=" + iconId +
                ", name='" + name + '\'' +
                '}';
    }
}
