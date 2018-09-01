package com.example.androidutils.bean;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;

import java.io.Serializable;

/**
 * Created by ZhangXinmin on 2018/9/1.
 * Copyright (c) 2018 . All rights reserved.
 */
public class ImageEntity implements Serializable {

    private int resId;
    private Resources resources;
    private Bitmap image;
    private String name;

    public ImageEntity() {
    }

    public ImageEntity(String name, Bitmap image) {
        this.image = image;
        this.name = name;
    }

    public ImageEntity(Resources resources, @StringRes int resId, Bitmap image) {
        this.name = resources.getString(resId);
        this.image = image;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ImageEntity{" +
                "resId=" + resId +
                ", image=" + image +
                ", name='" + name + '\'' +
                '}';
    }
}
