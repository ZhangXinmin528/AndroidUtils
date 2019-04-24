package com.example.androidutils.activity;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.androidutils.R;
import com.example.androidutils.base.BaseActivity;
import com.zxm.utils.core.encrypt.EncryptUtils;

/**
 * Created by ZhangXinmin on 2019/3/29.
 * Copyright (c) 2018 . All rights reserved.
 * 加解密工具的使用
 */
public class EncryptActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = EncryptActivity.class.getSimpleName();

    private String key = "TKIGDICMIDMMGHWZ";

    private Context mContext;
    private String encryptResult;

    private TextInputEditText mInputEt;
    private TextView mEncryptResultTv, mDecryptResultTv;

    @Override
    protected Object setLayout() {
        return R.layout.activity_encrypt;
    }

    @Override
    protected void initParamsAndValues() {
        mContext = this;
    }

    @Override
    protected void initViews() {

        mInputEt = findViewById(R.id.et_input);
        mEncryptResultTv = findViewById(R.id.tv_encrypt_result);
        mDecryptResultTv = findViewById(R.id.tv_decrypt_result);

        findViewById(R.id.btn_decrypt).setOnClickListener(this);
        findViewById(R.id.btn_encrypt).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //加密
            case R.id.btn_encrypt:
                doEncrypt();
                break;
            //解密
            case R.id.btn_decrypt:
                doDecrypt();
                break;
        }
    }

    /**
     * 加密
     */
    private void doEncrypt() {
        final String value = mInputEt.getText().toString().trim();
        if (!TextUtils.isEmpty(value)) {
            final StringBuilder sb = new StringBuilder();

            sb.append("\n").append("Original Data:").append("\n").append(value)
                    .append("\n").append("密钥：").append("\n").append(key);


            encryptResult = EncryptUtils.encryptAES2HexString(
                    hexString2Bytes(value),
                    key.getBytes(),
                    "AES/ECB/NoPadding",
                    null);

            sb.append("\n").append("加密结果：").append("\n").append(encryptResult);

            Log.e(TAG, sb.toString());

            if (!TextUtils.isEmpty(sb.toString())) {
                mEncryptResultTv.setText(sb.toString());
            }
        }
    }

    /**
     * 解密
     */
    private void doDecrypt() {
        encryptResult = "8239A7F58CE579737FC39724998C4368";
        if (!TextUtils.isEmpty(encryptResult)) {
            final StringBuilder sb = new StringBuilder();

            sb.append("\n").append("Encrypted Data:").append("\n").append(encryptResult)
                    .append("\n").append("密钥：").append("\n").append(key);

            final byte[] temp =
                    EncryptUtils.decryptHexStringAES(
                            encryptResult,
                            key.getBytes(),
                            "AES/ECB/NoPadding",
                            null
                    );

            final String originalData = EncryptUtils.bytes2HexString(temp);

            sb.append("\n").append("Original Data：").append("\n").append(originalData);

            Log.e(TAG, sb.toString());

            if (!TextUtils.isEmpty(sb.toString())) {
                mDecryptResultTv.setText(sb.toString());
            }
        }
    }

    private byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString)) return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Int(hexBytes[i]) << 4 | hex2Int(hexBytes[i + 1]));
        }
        return ret;
    }

    private boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private int hex2Int(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

}
