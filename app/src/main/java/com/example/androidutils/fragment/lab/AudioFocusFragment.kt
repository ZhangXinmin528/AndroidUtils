package com.example.androidutils.fragment.lab

import android.annotation.SuppressLint
import android.media.AudioManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.coding.zxm.annotation.Function
import com.coding.zxm.annotation.Group
import com.coding.zxm.lib_core.base.BaseFragment
import com.example.androidutils.R
import com.example.androidutils.databinding.FragmentAudioFocusBinding
import com.zxm.utils.core.audiofocus.AudioFocusHelper
import com.zxm.utils.core.audiofocus.OnAudioFocusChangedCallback

/**
 * Created by zhangxinmin on 2023/12/13.
 */
@SuppressLint("NonConstantResourceId")
@Function(group = Group.Lab, funcName = "音频焦点", funcIconRes = R.mipmap.icon_audio_focus)
class AudioFocusFragment : BaseFragment(), OnClickListener, OnAudioFocusChangedCallback {

    private lateinit var binding: FragmentAudioFocusBinding
    private lateinit var audioFocusHelper: AudioFocusHelper

    override fun setLayoutId(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentAudioFocusBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun initParamsAndValues() {
        binding.layoutTitle.tvToolbarTitle.text = "音频焦点"
        binding.layoutTitle.ivToolbarBack.setOnClickListener(this)

        if (!::audioFocusHelper.isInitialized)
            audioFocusHelper = mContext?.let { AudioFocusHelper(it) } ?: return

        //设置回调
        audioFocusHelper.setOnAudioFocusChangedCallback(this)

        binding.btnRequstFocus.setOnClickListener(this)
        binding.btnAbondonFocus.setOnClickListener(this)
        binding.btnStartMusic.setOnClickListener(this)
        binding.btnStopMusic.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_toolbar_back -> {
                popBackStack()
            }

            R.id.btn_requst_focus -> {
                audioFocusHelper.changeAudioFocus(
                    true,
                    focusGain = AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE
                )
            }

            R.id.btn_abondon_focus -> {
                audioFocusHelper.changeAudioFocus(false)
            }

            R.id.btn_start_music -> {

            }

            R.id.btn_stop_music -> {

            }
        }
    }

    override fun onAudioFocusLoss() {
        Log.d(sTAG, "${sTAG}..onAudioFocusLoss()~")
        binding.tvAudioFocusResult.text = "音频焦点：失去"
    }

    override fun onAudioFocusLossTransient() {
        Log.d(sTAG, "${sTAG}..onAudioFocusLossTransient()~")
        binding.tvAudioFocusResult.text = "音频焦点：失去"
    }

    override fun onAudioFocusLossTransientCanDuck() {
        Log.d(sTAG, "${sTAG}..onAudioFocusLossTransientCanDuck()~")
        binding.tvAudioFocusResult.text = "音频焦点：失去"
    }

    override fun onAudioFocusGain() {
        Log.d(sTAG, "${sTAG}..onAudioFocusGain()~")
        binding.tvAudioFocusResult.text = "音频焦点：获取"
    }
}