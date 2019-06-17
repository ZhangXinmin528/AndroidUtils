package com.example.androidutils;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.androidutils.activity.DeviceActivity;
import com.example.androidutils.activity.DialogActivity;
import com.example.androidutils.activity.EncryptActivity;
import com.example.androidutils.activity.ImageActivity;
import com.example.androidutils.activity.KeyboradActivity;
import com.example.androidutils.activity.LogActivity;
import com.example.androidutils.activity.NetWatcherActivity;
import com.example.androidutils.activity.PaletteActivity;
import com.example.androidutils.activity.PermissionActivity;
import com.example.androidutils.activity.PingActivity;
import com.example.androidutils.activity.ScreenActivity;
import com.example.androidutils.activity.SettingActivity;
import com.example.androidutils.activity.SpanActivity;
import com.example.androidutils.adapter.NavigationAdapter;
import com.example.androidutils.adapter.decoration.StaggeredItemDecoration;
import com.example.androidutils.base.BaseActivity;
import com.example.androidutils.bean.NaviEntity;
import com.example.androidutils.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnItemClickListener {

    private Context mContext;
    private List<NaviEntity> mDataList;
    private RecyclerView mRecyclerView;
    private NavigationAdapter mAdapter;

    @Override
    protected Object setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParamsAndValues() {
        mContext = this;
        mDataList = new ArrayList<>();
        mAdapter = new NavigationAdapter(mContext, mDataList);
    }

    @Override
    protected void initViews() {
        mRecyclerView = findViewById(R.id.rv_home);
        mRecyclerView.setAdapter(mAdapter);
        final StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(
                new StaggeredItemDecoration(mContext));
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        final String[] temp = getResources().getStringArray(R.array.sort_name_array);

        TypedArray iconArray = getResources().obtainTypedArray(R.array.sort_icon_array);

        final int length = temp.length;
        for (int i = 0; i < length; i++) {
            final NaviEntity entity = new NaviEntity();
            entity.setName(temp[i]);
            final int iconId = iconArray.getResourceId(i, -1);
            entity.setIconId(iconId);
            mDataList.add(entity);
        }

        mAdapter.notifyDataSetChanged();
        iconArray.recycle();
    }

    @Override
    public void onItemClick(@NonNull RecyclerView.Adapter adapter, @NonNull View view, int position) {
        Intent intent = new Intent();
        switch (position) {
            case 0:
                intent.setClass(mContext, DeviceActivity.class);
                break;
            case 1:
                intent.setClass(mContext, DialogActivity.class);
                break;
            case 2:
                intent.setClass(mContext, EncryptActivity.class);
                break;
            case 3:
                intent.setClass(mContext, ImageActivity.class);
                break;
            case 4:
                intent.setClass(mContext, KeyboradActivity.class);
                break;
            case 5:
                intent.setClass(mContext, LogActivity.class);
                break;
            case 6:
                intent.setClass(mContext, NetWatcherActivity.class);
                break;
            case 7:
//                intent.setClass(mContext, NetWatcherActivity.class);
                break;
            case 8:
                intent.setClass(mContext, PermissionActivity.class);
                break;
            case 9:
//                intent.setClass(mContext, PermissionActivity.class);
                break;
            case 10:
                intent.setClass(mContext, PingActivity.class);
                break;
            case 11:
                intent.setClass(mContext, SpanActivity.class);
                break;
            case 12:
                intent.setClass(mContext, ScreenActivity.class);
                break;
            case 13:
//                intent.setClass(mContext, PingActivity.class);
                break;
            case 14:
                intent.setClass(mContext, PaletteActivity.class);
                break;
            case 15:
                intent.setClass(mContext, SettingActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
