package com.example.androidutils.fragment

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.androidutils.ConfirmWindow
import com.example.androidutils.R
import com.example.androidutils.base.BaseFragment
import com.zxm.utils.core.dialog.EasyDialog
import com.zxm.utils.core.dialog.LoadingDialog
import kotlinx.android.synthetic.main.fragment_dialog.*

/**
 * Created by ZhangXinmin on 2018/10/13.
 * Copyright (c) 2018 . All rights reserved.
 * 展示Dialog的使用
 */
class DialogFragment : BaseFragment(), View.OnClickListener {

    private var mConfirmWindow: ConfirmWindow? = null

    override fun setLayoutId(): Int {
        return R.layout.fragment_dialog
    }

    override fun initViews(rootView: View) {
        rootView.findViewById<View>(R.id.btn_show_dialog).setOnClickListener(this)
        rootView.findViewById<View>(R.id.btn_show_window).setOnClickListener(this)
        rootView.findViewById<View>(R.id.btn_show_activity).setOnClickListener(this)
        rootView.findViewById<View>(R.id.btn_loading).setOnClickListener(this)
    }

    override fun initParamsAndValues() {
        mConfirmWindow = ConfirmWindow(mContext, activity)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_show_dialog -> EasyDialog.Builder(mContext!!)
                    .setContentView(R.layout.layout_confirm_dialog)
                    .setMessage("这是Dialog演示", R.id.tv_message)
                    .setWidth(250)
                    .setHeight(250)
                    .setCancelable(true)
                    .setPositiveButton("确定", R.id.tv_confirm) { Toast.makeText(mContext, "点击了确定", Toast.LENGTH_SHORT).show() }
                    .setNegativeButton("再想想", R.id.tv_cancel) { Toast.makeText(mContext, "点击了再想想", Toast.LENGTH_SHORT).show() }.showDialog()
            R.id.btn_show_window -> mConfirmWindow!!.showWindow(rl_root)
            R.id.btn_show_activity -> {
                val intent = Intent(mContext, ImitateDialogActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_loading -> LoadingDialog.Builder(mContext!!)
                    .setContentView(R.layout.layout_loading_dialog)
                    .setHeight(180)
                    .setWidth(180)
                    .setCancelable(true)
                    .setMessage(getString(R.string.all_loading), R.id.tv_loading_msg)
                    .showDialog()
        }
    }
}