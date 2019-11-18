package com.example.androidutils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidutils.R;
import com.example.androidutils.adapter.vh.NavigationVh;
import com.example.androidutils.bean.GlideApp;
import com.example.androidutils.bean.NaviEntity;
import com.example.androidutils.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by ZhangXinmin on 2019/6/17.
 * Copyright (c) 2018 . All rights reserved.
 */
public class NavigationAdapter extends RecyclerView.Adapter<NavigationVh> {

    private Context mContext;
    private List<NaviEntity> mDataList;
    private OnItemClickListener mListener;

    public NavigationAdapter(Context context, List<NaviEntity> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    @NonNull
    @Override
    public NavigationVh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_navigation_list_item, null);
        return new NavigationVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NavigationVh holder, final int position) {
        final NaviEntity entity = getItem(position);
        if (entity != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(NavigationAdapter.this, holder.itemView, position);
                    }
                }
            });

            GlideApp.with(mContext)
                    .load(entity.getIconId())
                    .into(holder.getIcon());

            holder.getName().setText(entity.getName());

        }
    }

    @Override
    public int getItemCount() {
        if (mDataList != null) {
            return mDataList.size();
        }
        return 0;
    }

    private NaviEntity getItem(int postion) {
        if (mDataList != null && !mDataList.isEmpty()) {
            return mDataList.get(postion);
        }
        return null;
    }

    /**
     * Item click
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
