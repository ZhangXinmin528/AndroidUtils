package com.example.androidutils.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.androidutils.R;
import com.example.androidutils.bean.GlideApp;
import com.example.androidutils.bean.ImageEntity;

import java.util.List;

/**
 * Created by ZhangXinmin on 2018/9/1.
 * Copyright (c) 2018 . All rights reserved.
 */
public class ImageAdapter extends BaseQuickAdapter<ImageEntity, BaseViewHolder> {

    private Context mContext;

    public ImageAdapter(Context context, @Nullable List<ImageEntity> data) {
        super(R.layout.layout_list_item_image, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, ImageEntity item) {
        holder.setText(R.id.tv_image_name, item.getName());
        final ImageView imageView = holder.getView(R.id.iv_image);
        GlideApp.with(mContext)
                .load(item.getImage())
                .into(imageView);
    }
}
