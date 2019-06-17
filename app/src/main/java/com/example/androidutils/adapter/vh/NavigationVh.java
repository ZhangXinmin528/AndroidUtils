package com.example.androidutils.adapter.vh;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidutils.R;

/**
 * Created by ZhangXinmin on 2019/6/17.
 * Copyright (c) 2018 . All rights reserved.
 */
public class NavigationVh extends RecyclerView.ViewHolder {

    private ImageView icon;
    private TextView name;

    public NavigationVh(@NonNull View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.iv_item_icon);
        name = itemView.findViewById(R.id.tv_item_name);

    }

    public ImageView getIcon() {
        return icon;
    }

    public TextView getName() {
        return name;
    }
}
