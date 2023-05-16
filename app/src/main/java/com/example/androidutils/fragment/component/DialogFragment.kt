package com.example.androidutils.fragment.component

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.ConfirmWindow
import com.example.androidutils.ImitateDialogActivity
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentDialogBinding
import com.zxm.utils.core.dialog.EasyDialog
import com.zxm.utils.core.dialog.LoadingDialog

/**
 * Created by ZhangXinmin on 2018/10/13.
 * Copyright (c) 2018 . All rights reserved.
 * 展示Dialog的使用
 */
@Function(group = Group.Component, funcName = "应用弹窗", funcIconRes = R.mipmap.icon_dialog)
class DialogFragment : BaseFragment(), View.OnClickListener {

    private var mConfirmWindow: ConfirmWindow? = null

    private lateinit var dialogBinding: FragmentDialogBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        dialogBinding = FragmentDialogBinding.inflate(inflater, container, false)
        return dialogBinding.root
    }

    override fun initParamsAndValues() {
        mConfirmWindow = ConfirmWindow(mContext, activity)

        dialogBinding.layoutTitle.tvToolbarTitle.text = "应用弹窗"
        dialogBinding.layoutTitle.ivToolbarBack.setOnClickListener(this)

        dialogBinding.btnShowDialog.setOnClickListener(this)
        dialogBinding.btnShowWindow.setOnClickListener(this)
        dialogBinding.btnShowActivity.setOnClickListener(this)
        dialogBinding.btnLoading.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }
            R.id.btn_show_dialog -> EasyDialog.Builder(mContext!!)
                .setContentView(R.layout.layout_confirm_dialog)
                .setMessage("这是Dialog演示", R.id.tv_message).setWidth(250).setHeight(250)
                .setCancelable(true).setPositiveButton("确定", R.id.tv_confirm) {
                    Toast.makeText(
                        mContext, "点击了确定", Toast.LENGTH_SHORT
                    ).show()
                }.setNegativeButton("再想想", R.id.tv_cancel) {
                    Toast.makeText(
                        mContext, "点击了再想想", Toast.LENGTH_SHORT
                    ).show()
                }.showDialog()
            R.id.btn_show_window -> mConfirmWindow!!.showWindow(dialogBinding.rlRoot)
            R.id.btn_show_activity -> {
                val intent = Intent(mContext, ImitateDialogActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_loading -> LoadingDialog.Builder(mContext!!)
                .setContentView(R.layout.layout_loading_dialog).setHeight(140).setWidth(140)
                .setCancelable(true)
                .setMessage(getString(R.string.all_loading), R.id.tv_loading_msg).showDialog()
        }
    }
}