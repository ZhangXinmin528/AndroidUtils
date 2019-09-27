package com.example.androidutils.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;

import com.example.androidutils.R;
import com.example.androidutils.base.BaseActivity;

/**
 * Created by ZhangXinmin on 2018/11/20.
 * Copyright (c) 2018.
 * 取色功能
 */
public class PaletteActivity extends BaseActivity {
    private Context mContext;

    private ImageView mSimpleIV;

    @Override
    protected Object setLayout() {
        return R.layout.activity_palette;
    }

    @Override
    protected void initParamsAndValues() {
        mContext = this;
        initActionBar();
    }

    @Override
    protected void initViews() {
        mSimpleIV = findViewById(R.id.iv_palette_simple);

        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.palette_simple);
        if (bitmap != null) {
            mSimpleIV.setImageBitmap(bitmap);

            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(@Nullable Palette palette) {
                    if (palette != null) {
                        //
                        Palette.Swatch swatch = palette.getVibrantSwatch();
                        final int bgColor = swatch.getRgb();
                        final int bodyColor = swatch.getBodyTextColor();
                        final int titleColor = swatch.getTitleTextColor();

                        final TextView textview = findViewById(R.id.tv_palette_simple);
                        textview.setBackgroundColor(bgColor);
                        textview.setTextColor(titleColor);

                    }
                }
            });
        }
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
