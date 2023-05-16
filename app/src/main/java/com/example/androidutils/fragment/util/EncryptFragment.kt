package com.example.androidutils.fragment.util

import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentEncryptBinding
import com.zxm.utils.core.encrypt.EncryptUtils

/**
 * Created by ZhangXinmin on 2019/3/29.
 * Copyright (c) 2018 . All rights reserved.
 * 加解密工具的使用
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.UTILS, funcName = "信息加密", funcIconRes = R.mipmap.icon_encrypt)
class EncryptFragment : BaseFragment(), View.OnClickListener {
    private val key = "42581693"
    private var encryptResult: String? = null
    private lateinit var encryptBinding: FragmentEncryptBinding

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        encryptBinding = FragmentEncryptBinding.inflate(inflater, container, false)
        return encryptBinding.root
    }

    override fun initParamsAndValues() {
        encryptBinding.layoutTitle.tvToolbarTitle.text = "信息加密"
        encryptBinding.layoutTitle.ivToolbarBack.setOnClickListener(this)

        encryptBinding.btnDecrypt.setOnClickListener(this)
        encryptBinding.btnEncrypt.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }
            R.id.btn_encrypt -> doEncrypt()
            R.id.btn_decrypt -> doDecrypt()
        }
    }

    /**
     * 加密
     */
    private fun doEncrypt() {
        val value = encryptBinding.etInput.text.toString().trim { it <= ' ' }
        if (!TextUtils.isEmpty(value)) {
            val sb = StringBuilder()
            sb.append("\n").append("Original Data:").append("\n").append(value)
                .append("\n").append("密钥：").append("\n").append(key)
            val temp = EncryptUtils.encryptDES2Base64(
                value.toByteArray(),
                key.toByteArray(),
                "DES/CBC/PKCS5Padding",
                IV
            )
            encryptResult = String(temp)
            sb.append("\n").append("加密结果：").append("\n").append(encryptResult)
            Log.e(Companion.TAG, sb.toString())
            if (!TextUtils.isEmpty(sb.toString())) {
                encryptBinding.tvEncryptResult.text = sb.toString()
            }
        }
    }

    /**
     * 解密
     */
    private fun doDecrypt() {
        encryptResult =
            "KyO4wXSQ64bTAw1ASO93EAqhKQ/qxDCrCA7fQz6pkckECacR6u3Bv3fn+06y 4ZCWW7zxSWztmSpxEGs6yJUdqOAN8/sZSpcrZmZPunuDuGlfNVnHTBcLOEKB tnZkQU+7AF7q8xiOc+LDt01cBQIKXw+LdMQrYNDEj+FLEZB3Lv9ZQsOo3EqZ FMKorYGCaHobzT9zH7t8ZPY2r/aRJM7PbkCPvZovYJH7X83GLutHqy3FrVRl 2QQqwu6w8IKyXKKRJbtUnk6D3+1I56lN+SCEYw=="
        if (!TextUtils.isEmpty(encryptResult)) {
            val sb = StringBuilder()
            sb.append("\n").append("Encrypted Data:").append("\n").append(encryptResult)
                .append("\n").append("密钥：").append("\n").append(key)
            val temp = EncryptUtils.decryptBase64DES(
                encryptResult!!.toByteArray(),
                key.toByteArray(),
                "DES/CBC/PKCS5Padding",
                IV
            )
            val originalData = String(temp)
            sb.append("\n").append("Original Data：").append("\n").append(originalData)
            Log.e(Companion.TAG, sb.toString())
            if (!TextUtils.isEmpty(sb.toString())) {
                encryptBinding.tvDecryptResult.text = sb.toString()
            }
        }
    }

    companion object {
        private val TAG = EncryptFragment::class.java.simpleName
        private val IV = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
    }
}