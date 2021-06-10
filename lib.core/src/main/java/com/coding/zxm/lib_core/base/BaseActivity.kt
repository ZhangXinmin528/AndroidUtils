package com.coding.zxm.lib_core.base

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.coding.zxm.lib_core.R
import com.coding.zxm.lib_core.constant.SPConfig
import com.zxm.utils.core.bar.StatusBarCompat
import com.zxm.utils.core.sp.SharedPreferencesUtil

/**
 * Created by ZhangXinmin on 2020/7/19.
 * Copyright (c) 2020 . All rights reserved.
 * Base activity~
 */
abstract class BaseActivity : AppCompatActivity() {
    protected val TAG = this.javaClass.simpleName

    protected var mContext: Context? = null

    abstract fun setLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(setLayoutId())

        mContext = this

        initParamsAndValues()

        initViews()
    }

    /**
     * 设置字体大小
     */
    override fun getResources(): Resources {
        val res = super.getResources()
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            val config: Configuration = res.configuration
            config.fontScale =
                    SharedPreferencesUtil.get(
                            this,
                            SPConfig.CONFIG_FONT_SCALE,
                            1.0f
                    ) as Float
            res.updateConfiguration(config, res.displayMetrics)
        }
        return res
    }

    /**
     * 设置字体大小
     */
    override fun attachBaseContext(newBase: Context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            val res = newBase.resources
            val configuration = res.configuration

            configuration.let {

                it.fontScale = SharedPreferencesUtil.get(
                        newBase,
                        SPConfig.CONFIG_FONT_SCALE,
                        1.0f
                ) as Float

                val newContext = newBase.createConfigurationContext(it)
                super.attachBaseContext(newContext)
            }
        } else {
            super.attachBaseContext(newBase)
        }
    }

    abstract fun initParamsAndValues()

    abstract fun initViews()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun setStatusBarColor() {
        StatusBarCompat.setColor(this, resources.getColor(R.color.color_state_bar))
    }

    protected fun setStatusBarColorWhite() {
        StatusBarCompat.setColorNoTranslucent(this, Color.WHITE)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    protected fun initActionBar(toolbar: Toolbar, titile: String) {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.let {
            actionBar.title = titile
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
    }

    protected fun jumpActivity(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    protected fun jumpActivity(clazz: Class<*>) {
        val intent = Intent(mContext, clazz)
        jumpActivity(intent)
    }

    protected fun jumpActivity(bundle: Bundle, clazz: Class<*>) {
        val intent = Intent(mContext, clazz)
        intent.putExtras(bundle)
        jumpActivity(intent)
    }

}