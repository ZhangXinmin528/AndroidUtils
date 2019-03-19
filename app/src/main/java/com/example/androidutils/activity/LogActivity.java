package com.example.androidutils.activity;

import android.app.ActionBar;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.example.androidutils.R;
import com.example.androidutils.base.BaseActivity;
import com.zxm.libutils.log.MLogger;

/**
 * Created by ZhangXinmin on 2019/3/19.
 * Copyright (c) 2018 . All rights reserved.
 */
public class LogActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = LogActivity.class.getSimpleName();

    private static final int UPDATE_LOG = 0x01;
    private static final int UPDATE_CONSOLE = 0x01 << 1;
    private static final int UPDATE_TAG = 0x01 << 2;
    private static final int UPDATE_HEAD = 0x01 << 3;
    private static final int UPDATE_FILE = 0x01 << 4;
    private static final int UPDATE_DIR = 0x01 << 5;
    private static final int UPDATE_BORDER = 0x01 << 6;
    private static final int UPDATE_SINGLE = 0x01 << 7;
    private static final int UPDATE_CONSOLE_FILTER = 0x01 << 8;
    private static final int UPDATE_FILE_FILTER = 0x01 << 9;

    private static final String longStr;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("len = 10400\ncontent = \"");
        for (int i = 0; i < 800; ++i) {
            sb.append("Hello world. ");
        }
        sb.append("\"");
        longStr = sb.toString();
    }

    private String dir = "";
    private String globalTag = "";
    private boolean log = true;
    private boolean console = true;
    private boolean head = true;
    private boolean file = false;
    private boolean border = true;
    private boolean single = true;
    private int consoleFilter = MLogger.V;
    private int fileFilter = MLogger.V;
    private TextView tvAboutLog;
    private MLogger.ConfigBuilder builder;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            MLogger.v("verbose");
            MLogger.d("debug");
            MLogger.i("info");
            MLogger.w("warn");
            MLogger.e("error");
            MLogger.a("assert");
        }
    };

    @Override
    protected Object setLayout() {
        return R.layout.activity_log;
    }

    @Override
    protected void initParamsAndValues() {
        builder = MLogger.getLogConfig();
    }

    @Override
    protected void initViews() {

        //默认设置
        tvAboutLog = findViewById(R.id.tv_about_log);
        tvAboutLog.setText(builder.toString());

        findViewById(R.id.btn_toggle_log).setOnClickListener(this);
        findViewById(R.id.btn_toggle_console).setOnClickListener(this);
        findViewById(R.id.btn_toggle_tag).setOnClickListener(this);
        findViewById(R.id.btn_toggle_head).setOnClickListener(this);
        findViewById(R.id.btn_toggle_border).setOnClickListener(this);
        findViewById(R.id.btn_toggle_single).setOnClickListener(this);
        findViewById(R.id.btn_toggle_file).setOnClickListener(this);
        findViewById(R.id.btn_toggle_dir).setOnClickListener(this);
        findViewById(R.id.btn_toggle_conole_filter).setOnClickListener(this);
        findViewById(R.id.btn_toggle_file_filter).setOnClickListener(this);
        findViewById(R.id.btn_log_no_tag).setOnClickListener(this);
        findViewById(R.id.btn_log_with_tag).setOnClickListener(this);
        findViewById(R.id.btn_log_in_new_thread).setOnClickListener(this);
        findViewById(R.id.btn_log_null).setOnClickListener(this);
        findViewById(R.id.btn_log_many_params).setOnClickListener(this);
        findViewById(R.id.btn_log_long).setOnClickListener(this);
        findViewById(R.id.btn_log_file).setOnClickListener(this);
        findViewById(R.id.btn_log_json).setOnClickListener(this);
        findViewById(R.id.btn_log_xml).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_toggle_log:
                updateConfig(UPDATE_LOG);
                break;
            case R.id.btn_toggle_console:
                updateConfig(UPDATE_CONSOLE);
                break;
            case R.id.btn_toggle_tag:
                updateConfig(UPDATE_TAG);
                break;
            case R.id.btn_toggle_head:
                updateConfig(UPDATE_HEAD);
                break;
            case R.id.btn_toggle_file:
                updateConfig(UPDATE_FILE);
                break;
            case R.id.btn_toggle_dir:
                updateConfig(UPDATE_DIR);
                break;
            case R.id.btn_toggle_border:
                updateConfig(UPDATE_BORDER);
                break;
            case R.id.btn_toggle_single:
                updateConfig(UPDATE_SINGLE);
                break;
            case R.id.btn_toggle_conole_filter:
                updateConfig(UPDATE_CONSOLE_FILTER);
                break;
            case R.id.btn_toggle_file_filter:
                updateConfig(UPDATE_FILE_FILTER);
                break;
            case R.id.btn_log_no_tag:
                MLogger.v("verbose");
                MLogger.d("debug");
                MLogger.i("info");
                MLogger.w("warn");
                MLogger.e("error");
                MLogger.a("assert");
                break;
            case R.id.btn_log_with_tag:
                MLogger.vTag("customTag", "verbose");
                MLogger.dTag("customTag", "debug");
                MLogger.iTag("customTag", "info");
                MLogger.wTag("customTag", "warn");
                MLogger.eTag("customTag", "error");
                MLogger.aTag("customTag", "assert");
                break;
            case R.id.btn_log_in_new_thread:
                Thread thread = new Thread(mRunnable);
                thread.start();
                break;
            case R.id.btn_log_null:
                MLogger.v((Object) null);
                MLogger.d((Object) null);
                MLogger.i((Object) null);
                MLogger.w((Object) null);
                MLogger.e((Object) null);
                MLogger.a((Object) null);
                break;
            case R.id.btn_log_many_params:
                MLogger.v("verbose0", "verbose1");
                MLogger.vTag("customTag", "verbose0", "verbose1");
                MLogger.d("debug0", "debug1");
                MLogger.dTag("customTag", "debug0", "debug1");
                MLogger.i("info0", "info1");
                MLogger.iTag("customTag", "info0", "info1");
                MLogger.w("warn0", "warn1");
                MLogger.wTag("customTag", "warn0", "warn1");
                MLogger.e("error0", "error1");
                MLogger.eTag("customTag", "error0", "error1");
                MLogger.a("assert0", "assert1");
                MLogger.aTag("customTag", "assert0", "assert1");
                break;
            case R.id.btn_log_long:
                MLogger.d(longStr);
                break;
            case R.id.btn_log_file:
                for (int i = 0; i < 15; i++) {
                    MLogger.file("test0 log to file");
//                    MLogger.file(MLogger.I, "test0 log to file");
                }
                break;
            case R.id.btn_log_json:
                String json = "{\"tools\": [{ \"name\":\"css format\" , \"site\":\"http://tools.w3cschool.cn/code/css\" },{ \"name\":\"json format\" , \"site\":\"http://tools.w3cschool.cn/code/json\" },{ \"name\":\"pwd check\" , \"site\":\"http://tools.w3cschool.cn/password/my_password_safe\" }]}";
                MLogger.json(json);
//                MLogger.json(MLogger.I, json);
                break;
            case R.id.btn_log_xml:
                String xml = "<books><book><author>Jack Herrington</author><title>PHP Hacks</title><publisher>O'Reilly</publisher></book><book><author>Jack Herrington</author><title>Podcasting Hacks</title><publisher>O'Reilly</publisher></book></books>";
                MLogger.xml(xml);
//                MLogger.xml(MLogger.I, xml);
                break;
        }
    }

    private void updateConfig(int args) {
        switch (args) {
            case UPDATE_LOG:
                log = !log;
                break;
            case UPDATE_CONSOLE:
                console = !console;
                break;
            case UPDATE_TAG:
                globalTag = globalTag.equals(TAG) ? "" : TAG;
                break;
            case UPDATE_HEAD:
                head = !head;
                break;
            case UPDATE_FILE:
                file = !file;
                break;
            case UPDATE_DIR:
                if (getDir().contains("test")) {
                    dir = null;
                } else {
                    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                        dir = Environment.getExternalStorageDirectory().getPath() + System.getProperty("file.separator") + "test";
                    }
                }
                break;
            case UPDATE_BORDER:
                border = !border;
                break;
            case UPDATE_SINGLE:
                single = !single;
                break;
            case UPDATE_CONSOLE_FILTER:
                consoleFilter = consoleFilter == MLogger.V ? MLogger.W : MLogger.V;
                break;
            case UPDATE_FILE_FILTER:
                fileFilter = fileFilter == MLogger.V ? MLogger.I : MLogger.V;
                break;
        }
        builder.setLogSwitch(log)
                .setConsoleSwitch(console)
                .setGlobalTag(globalTag)
                .setLogHeadSwitch(head)
                .setLog2FileSwitch(file)
                .setDir(dir)
                .setBorderSwitch(border)
                .setSingleTagSwitch(single)
                .setConsoleFilter(consoleFilter)
                .setFileFilter(fileFilter);
        tvAboutLog.setText(builder.toString());
    }

    private String getDir() {
        return builder.toString().split(System.getProperty("line.separator"))[5].substring(5);
    }
}
