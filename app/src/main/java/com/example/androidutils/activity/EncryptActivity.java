package com.example.androidutils.activity;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.androidutils.R;
import com.example.androidutils.base.BaseActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.zxm.utils.core.encrypt.EncryptUtils;

/**
 * Created by ZhangXinmin on 2019/3/29.
 * Copyright (c) 2018 . All rights reserved.
 * 加解密工具的使用
 */
public class EncryptActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = EncryptActivity.class.getSimpleName();

    private final static byte[] IV = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};

    private String key = "42581693";

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
        initActionBar();
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


            final byte[] temp = EncryptUtils.encryptDES2Base64(
                    value.getBytes(),
                    key.getBytes(),
                    "DES/CBC/PKCS5Padding",
                    IV);

            encryptResult = new String(temp);

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
        encryptResult = "KyO4wXSQ64bTAw1ASO93EAqhKQ/qxDCrCA7fQz6pkckECacR6u3Bv3fn+06y 4ZCWW7zxSWztmSpxEGs6yJUdqOAN8/sZSpcrZmZPunuDuGlfNVnHTBcLOEKB tnZkQU+7AF7q8xiOc+LDt01cBQIKXw+LdMQrYNDEj+FLEZB3Lv9ZQsOo3EqZ FMKorYGCaHobzT9zH7t8ZPY2r/aRJM7PbkCPvZovYJH7X83GLutHqy3FrVRl 2QQqwu6w8IKyXKKRJbtUnk6D3+1I56lN+SCEYw==";

        if (!TextUtils.isEmpty(encryptResult)) {
            final StringBuilder sb = new StringBuilder();

            sb.append("\n").append("Encrypted Data:").append("\n").append(encryptResult)
                    .append("\n").append("密钥：").append("\n").append(key);

            final byte[] temp =
                    EncryptUtils.decryptBase64DES(
                            encryptResult.getBytes(),
                            key.getBytes(),
                            "DES/CBC/PKCS5Padding",
                            IV
                    );

            final String originalData = new String(temp);

            sb.append("\n").append("Original Data：").append("\n").append(originalData);

            Log.e(TAG, sb.toString());

            if (!TextUtils.isEmpty(sb.toString())) {
                mDecryptResultTv.setText(sb.toString());
            }
        }
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
