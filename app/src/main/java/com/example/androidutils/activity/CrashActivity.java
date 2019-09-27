package com.example.androidutils.activity;

import android.content.DialogInterface;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidutils.R;
import com.example.androidutils.base.BaseActivity;
import com.zxm.utils.core.crash.CrashManager;
import com.zxm.utils.core.dialog.DialogUtil;

/**
 * Created by ZhangXinmin on 2019/8/12.
 * Copyright (c) 2019 . All rights reserved.
 */
public class CrashActivity extends BaseActivity implements View.OnClickListener {

    private SwitchCompat mSwitch;
    private TextView mEmptyTv;

    private RecyclerView mRecyclerView;
    private TextView mCrashSizeTv;

    @Override
    protected Object setLayout() {
        return R.layout.activity_crash;
    }

    @Override
    protected void initParamsAndValues() {

        initActionBar();
    }

    @Override
    protected void initViews() {
        mSwitch = findViewById(R.id.switch_crash);

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(mContext, "状态 ： " + isChecked, Toast.LENGTH_SHORT).show();
                if (isChecked) {
                    CrashManager.getInstance().startCapture();
                } else {
                    CrashManager.getInstance().stopCapture();
                }
            }
        });

        mEmptyTv = findViewById(R.id.tv_crash_empty);

        mRecyclerView = findViewById(R.id.rv_crash_result);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mCrashSizeTv = findViewById(R.id.tv_crash_file_size);
        mCrashSizeTv.setText(CrashManager.getInstance().getCrashFilesMemorySize());

        findViewById(R.id.layout_clear_crash).setOnClickListener(this);

        findViewById(R.id.tv_imitate_crash).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_clear_crash:
                DialogUtil.showDialog(mContext, "是否清除崩溃日志信息？", true, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final boolean state = CrashManager.getInstance().clearCrashFiles();

                        if (state) {
                            mCrashSizeTv.setText(CrashManager.getInstance().getCrashFilesMemorySize());
                        }
                    }
                });
                break;
            case R.id.tv_imitate_crash:
                imitateCrash();
                break;
        }
    }

    private void imitateCrash() {
        TextView nullTv = null;
        nullTv.setText("");
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
