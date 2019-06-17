package com.example.androidutils.adapter.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ZhangXinmin on 2019/6/17.
 * Copyright (c) 2018 . All rights reserved.
 */
public class StaggeredItemDecoration extends RecyclerView.ItemDecoration {

    //获取系统提供的分割线属性，并初始化单元素数组ATTRS
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Context mContext;
    //分割线
    private Drawable mDivider;

    //分割线高度
    private int mDividerHeight;

    //分割线宽度
    private int mDividerWidth;

    public StaggeredItemDecoration(Context context) {
        this.mContext = context;

        TypedArray a = context.obtainStyledAttributes(ATTRS);
        // 此处获取reference不同于Boolean、Integer等的获取方式，类似的还有getText()、getString()
        mDivider = a.getDrawable(0);
        a.recycle();

        //假设分割线的基础单元是1*1，宽高必须定义
        mDividerWidth = 1;
        mDividerHeight = 1;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0/*left*/, 0/*top*/, 1/*right*/, 1/*bottom*/);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

    }

    /**
     * 绘制水平分割线
     *
     * @param c
     * @param recyclerView
     */
    private void drawHorizontal(Canvas c, RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount(); // visible children【看不见，不用绘制，系统也不调用。】
        if (childCount <= 1) { //只有一个不分割 return;
        }
        for (int i = 0; i < childCount; i++) {
            // 获取当前的itemView，child就是itemView及其布局参数
            View child = recyclerView.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - layoutParams.leftMargin;//左边起点，别忘了去掉margin和padding
            int top = child.getBottom() + layoutParams.bottomMargin;// 水平线矩形在下边
            // 注意，mDividerWidth是竖直分割线矩形宽度，要加上
            int right = child.getRight() + layoutParams.rightMargin + mDividerWidth;
            // 注意，mDividerHeight是水平分割线矩形宽度，要加上
            int bottom = child.getBottom() + layoutParams.bottomMargin + mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * 绘制垂直分割线
     *
     * @param c
     * @param recyclerView
     */
    private void drawVertical(Canvas c, RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount(); // visible children
        int itemCount = recyclerView.getAdapter().getItemCount();// total items
        if (childCount <= 1) {
            return;
        }
        for (int i = 0; i < childCount; i++) {
            View child = recyclerView.getChildAt(i); // child就是itemView
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + layoutParams.rightMargin; // 竖直分割线在右边
            int top = child.getTop() - layoutParams.topMargin; //需要减去顶部布局margin
            // 注意，mDividerWidth是竖直分割线矩形宽度，要加上
            int right = child.getRight() + layoutParams.rightMargin + mDividerWidth;
            // 注意，mDividerHeight是水平分割线矩形宽度，绘制水平线时已经添加，这里不可以再添加
            int bottom = child.getBottom() + layoutParams.bottomMargin /*+ mDividerHeight*/;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
