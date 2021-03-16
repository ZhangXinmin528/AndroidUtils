package com.example.androidutils

import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.androidutils.R
import com.example.androidutils.base.BaseActivity

/**
 * Created by ZhangXinmin on 2018/10/16.
 * Copyright (c) 2018 . All rights reserved.
 * 模拟弹窗
 */
class ImitateDialogActivity : BaseActivity(), View.OnClickListener {
    override fun initParamsAndValues() {}
    override fun setLayoutId(): Int {
        return R.layout.activity_imitate_dialog
    }

    override fun initViews() {
        val message = findViewById<TextView>(R.id.tv_message)
        message.text = "Activity模拟Dialog"
        findViewById<View>(R.id.tv_cancel).setOnClickListener(this)
        findViewById<View>(R.id.tv_confirm).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_cancel -> finish()
            R.id.tv_confirm -> finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}