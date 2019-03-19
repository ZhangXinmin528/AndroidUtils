package com.example.androidutils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidutils.activity.DeviceActivity;
import com.example.androidutils.activity.DialogActivity;
import com.example.androidutils.activity.ImageActivity;
import com.example.androidutils.activity.KeyboradActivity;
import com.example.androidutils.activity.LogActivity;
import com.example.androidutils.activity.NetWatcherActivity;
import com.example.androidutils.activity.PaletteActivity;
import com.example.androidutils.activity.PermissionActivity;
import com.example.androidutils.activity.ScreenActivity;
import com.example.androidutils.activity.SpanActivity;
import com.example.androidutils.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private Context mContext;
    private ListView mListView;
    private List<String> mDataList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected Object setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParamsAndValues() {
        mContext = this;
        mDataList = new ArrayList<>();
        mAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, mDataList);
    }

    @Override
    protected void initViews() {
        mListView = findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mDataList.add("文字样式-->SpanUtils的使用");
        mDataList.add("屏幕属性-->ScreenUtil的使用");
        mDataList.add("软键盘使用-->KeyboradUtil的使用");
        mDataList.add("图片工具-->ImageUtil的使用");
        mDataList.add("自定义弹窗-->Dialog的使用");
        mDataList.add("网络检测-->NetWatchdog的使用");
        mDataList.add("图片取色-->Palette的使用");
        mDataList.add("获取设备信息-->DeviceUtil的使用");
        mDataList.add("请求权限-->Android权限申请");
        mDataList.add("日志工具-->日志输出和存储");
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        switch (position) {
            case 0:
                intent.setClass(mContext, SpanActivity.class);
                break;
            case 1:
                intent.setClass(mContext, ScreenActivity.class);
                break;
            case 2:
                intent.setClass(mContext, KeyboradActivity.class);
                break;
            case 3:
                intent.setClass(mContext, ImageActivity.class);
                break;
            case 4:
                intent.setClass(mContext, DialogActivity.class);
                break;
            case 5:
                intent.setClass(mContext, NetWatcherActivity.class);
                break;
            case 6:
                intent.setClass(mContext, PaletteActivity.class);
                break;
            case 7:
                intent.setClass(mContext, DeviceActivity.class);
                break;
            case 8:
                intent.setClass(mContext, PermissionActivity.class);
                break;
            case 9:
                intent.setClass(mContext, LogActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }

}
