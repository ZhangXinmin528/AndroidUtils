package com.example.androidutils.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidutils.R;

/**
 * Created by ZhangXinmin on 2018/11/20.
 * Copyright (c) 2018.
 * 取色功能
 */
public class PaletteActivity extends AppCompatActivity {
    private Context mContext;

    private ImageView mSimpleIV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);

        initParamsAndValues();
        initViews();
    }

    private void initParamsAndValues() {
        mContext = this;
    }

    private void initViews() {
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
}
