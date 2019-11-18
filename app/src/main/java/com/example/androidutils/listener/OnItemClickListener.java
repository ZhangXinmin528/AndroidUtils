package com.example.androidutils.listener;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by ZhangXinmin on 2019/1/8.
 * Copyright (c) 2018 . All rights reserved.
 */
public interface OnItemClickListener {

    /**
     * OnItemClick{@link RecyclerView}
     *
     * @param adapter {@link RecyclerView.Adapter}
     * @param view     itemview
     * @param position position
     */
    void onItemClick(@NonNull RecyclerView.Adapter adapter, @NonNull View view, int position);
}
