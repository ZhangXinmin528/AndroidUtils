package com.example.androidutils.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.androidutils.R;
import com.example.androidutils.bean.ImageEntity;

import java.io.ByteArrayOutputStream;
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
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        item.getImage().compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        Glide.with(mContext)
                .load(bytes)
                .into(imageView);

    }
}
