package com.example.androidutils.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.androidutils.R;
import com.example.androidutils.adapter.ImageAdapter;
import com.example.androidutils.base.BaseActivity;
import com.example.androidutils.bean.ImageEntity;
import com.zxm.utils.core.image.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangXinmin on 2018/9/1.
 * Copyright (c) 2018 . All rights reserved.
 * User guide for{@link ImageUtil}
 */
public class ImageActivity extends BaseActivity {
    private Context mContext;
    private Resources mResources;
    private Bitmap src;
    private List<ImageEntity> mList = new ArrayList<>();

    @Override
    protected Object setLayout() {
        return R.layout.activity_image;
    }

    @Override
    protected void initParamsAndValues() {
        mContext = this;
        mResources = mContext.getResources();

        initActionBar();
    }

    @Override
    protected void initViews() {
        RecyclerView rvImages = findViewById(R.id.rv_image);

        src = ImageUtil.getBitmap(mContext, R.drawable.img_lena);
        Bitmap round = ImageUtil.getBitmap(mContext, R.drawable.avatar_round);
        Bitmap watermark = ImageUtil.getBitmap(mContext, R.mipmap.ic_launcher);

        int width = src.getWidth();
        int height = src.getHeight();

        mList.add(new ImageEntity(mResources, R.string.image_src, src));
        mList.add(new ImageEntity(mResources, R.string.image_add_color, ImageUtil.drawColor(src, Color.parseColor("#8000FF00"))));
        mList.add(new ImageEntity(mResources, R.string.image_scale, ImageUtil.scale(src, width / 2, height / 2)));
        mList.add(new ImageEntity(mResources, R.string.image_clip, ImageUtil.clip(src, (width - width / 2) / 2, (height - height / 2) / 2, width / 2, height / 2)));
        mList.add(new ImageEntity(mResources, R.string.image_skew, ImageUtil.skew(src, 0.2f, 0.1f)));
        mList.add(new ImageEntity(mResources, R.string.image_rotate, ImageUtil.rotate(src, 90, width / 2, height / 2)));
        mList.add(new ImageEntity(mResources, R.string.image_to_round, ImageUtil.toRound(src)));
        mList.add(new ImageEntity(mResources, R.string.image_to_round_border, ImageUtil.toRound(src, 16, Color.GREEN)));
        mList.add(new ImageEntity(mResources, R.string.image_to_round_corner, ImageUtil.toRoundCorner(src, 80)));
        mList.add(new ImageEntity(mResources, R.string.image_to_round_corner_border, ImageUtil.toRoundCorner(src, 80, 16, Color.GREEN)));
        mList.add(new ImageEntity(mResources, R.string.image_add_corner_border, ImageUtil.addCornerBorder(src, 16, Color.GREEN, 0)));
        mList.add(new ImageEntity(mResources, R.string.image_add_circle_border, ImageUtil.addCircleBorder(round, 16, Color.GREEN)));
        mList.add(new ImageEntity(mResources, R.string.image_add_reflection, ImageUtil.addReflection(src, 80)));
        mList.add(new ImageEntity(mResources, R.string.image_add_text_watermark, ImageUtil.addTextWatermark(src, "blankj", 40, Color.GREEN, 0, 0)));
        mList.add(new ImageEntity(mResources, R.string.image_add_image_watermark, ImageUtil.addImageWatermark(src, watermark, 0, 0, 0x88)));
        mList.add(new ImageEntity(mResources, R.string.image_to_gray, ImageUtil.toGray(src)));
        mList.add(new ImageEntity(mResources, R.string.image_fast_blur, ImageUtil.fastBlur(mContext, src, 0.1f, 5)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mList.add(new ImageEntity(mResources, R.string.image_render_script_blur, ImageUtil.renderScriptBlur(mContext, src, 10)));
        }
        mList.add(new ImageEntity(mResources, R.string.image_stack_blur, ImageUtil.stackBlur(src, 10)));
        mList.add(new ImageEntity(mResources, R.string.image_compress_by_scale, ImageUtil.compressByScale(src, 0.5f, 0.5f)));
        mList.add(new ImageEntity(mResources, R.string.image_compress_by_quality_half, ImageUtil.compressByQuality(src, 50)));
        mList.add(new ImageEntity(mResources, R.string.image_compress_by_quality_max_size, ImageUtil.compressByQuality(src, 10L * 1024)));// 10Kb
        mList.add(new ImageEntity(mResources, R.string.image_compress_by_sample_size, ImageUtil.compressBySampleSize(src, 2)));

        rvImages.setAdapter(new ImageAdapter(mContext, mList));
        rvImages.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
