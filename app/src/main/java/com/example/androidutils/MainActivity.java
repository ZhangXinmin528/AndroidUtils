package com.example.androidutils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
            default:
                break;
        }
        startActivity(intent);
    }
}
