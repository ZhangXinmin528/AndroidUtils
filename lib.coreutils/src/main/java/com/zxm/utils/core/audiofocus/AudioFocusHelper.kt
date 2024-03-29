package com.zxm.utils.core.audiofocus

import android.annotation.TargetApi
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import android.util.Log
import com.zxm.utils.core.BuildConfig
import com.zxm.utils.core.device.AndroidUtil

private const val TAG = "AudioFocusHelper"

/**
 * Created by zhangxinmin on 2023/12/13.
 * 音频焦点相关管理类
 * 音频焦点
 * AUDIOFOCUS_GAIN:用于时间未知的音频焦点的获取请求,释放焦点后其他应用无法获取焦点；
 * AUDIOFOCUS_GAIN_TRANSIENT:用于持续时间很短的音频焦点的获取请求，例如事件通知或者驾驶场景；释放焦点后其他应用可以获取焦点；
 * AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:用于持续时间很短的音频焦点请求，并且允许其他应用降低输出级别以继续播放，例如驾驶场景中背景音乐的播放；
 * AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE:用于持续时间很短的音频焦点请求，在此期间其他应用和组件不允许播放任何内容，包括通知.使用场景为语音备忘录和语音识别；
 *
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class AudioFocusHelper(
    val context: Context
) {

    private lateinit var audioManager: AudioManager
    private lateinit var audioFocusRequest: AudioFocusRequest
    private var focusChangedCallback: OnAudioFocusChangedCallback? = null
    private val audioFocusListener = createOnAudioFocusChangeListener()

    private var hasAudioFocus = false

    @Volatile
    internal var lossTransient = false

    /**
     * Change the audio focus.
     *
     * @param acquire
     * @param focusGain AudioManager.AUDIOFOCUS_GAIN or AUDIOFOCUS_GAIN_TRANSIENT
     *                  or AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK or AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE;
     * @param contentType AudioAttributes.CONTENT_TYPE_MOVIE or AudioAttributes.CONTENT_TYPE_MUSIC
     */
    fun changeAudioFocus(
        acquire: Boolean,
        focusGain: Int = AudioManager.AUDIOFOCUS_GAIN,
        contentType: Int = AudioAttributes.CONTENT_TYPE_MUSIC
    ) {
        if (!this::audioManager.isInitialized) audioManager =
            context.getSystemService(Context.AUDIO_SERVICE) as AudioManager? ?: return

        if (acquire) {
            if (!hasAudioFocus) {
                val result = requestAudioFocus(focusGain = focusGain, contentType = contentType)
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    audioManager.setParameters("bgm_state=true")
                    hasAudioFocus = true
                    focusChangedCallback?.onAudioFocusGain()
                } else {
                    hasAudioFocus = false
                    focusChangedCallback?.onAudioFocusLoss()
                }
                Log.d(
                    TAG,
                    "requestAudioFocus..result:" + "${result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED}"
                )
            }
        } else if (hasAudioFocus) {
            val result = abandonAudioFocus()
            audioManager.setParameters("bgm_state=false")
            hasAudioFocus = false
            focusChangedCallback?.onAudioFocusLoss()
            Log.d(
                TAG,
                "abandonAudioFocus().. result:${result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED}"
            )
        }
    }


    @TargetApi(Build.VERSION_CODES.O)
    private fun abandonAudioFocus() = if (AndroidUtil.isOOrLater) {
        audioManager.abandonAudioFocusRequest(audioFocusRequest)
    } else {
        audioManager.abandonAudioFocus(audioFocusListener)
    }

    /**
     * 请求音频焦点
     * @param focusGain AudioManager.AUDIOFOCUS_GAIN or AUDIOFOCUS_GAIN_TRANSIENT
     *                  or AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK or AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE;
     * @param contentType AudioAttributes.CONTENT_TYPE_MOVIE or AudioAttributes.CONTENT_TYPE_MUSIC;
     */
    @TargetApi(Build.VERSION_CODES.O)
    private fun requestAudioFocus(
        focusGain: Int = AudioManager.AUDIOFOCUS_GAIN,
        contentType: Int = AudioAttributes.CONTENT_TYPE_MUSIC
    ) = if (AndroidUtil.isOOrLater) {
        val attributes = AudioAttributes.Builder().setContentType(contentType).build()

        audioFocusRequest =
            AudioFocusRequest.Builder(focusGain).setOnAudioFocusChangeListener(audioFocusListener)
                .setAudioAttributes(attributes).build()

        audioManager.requestAudioFocus(audioFocusRequest)
    } else {
        audioManager.requestAudioFocus(
            audioFocusListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN
        )
    }

    /**
     * 设置回调
     */
    fun setOnAudioFocusChangedCallback(callback: OnAudioFocusChangedCallback) {
        focusChangedCallback = callback
    }

    /**
     * 音频焦点状态
     */
    fun getAudioFocusState():Boolean{
        return hasAudioFocus
    }
    private fun createOnAudioFocusChangeListener(): AudioManager.OnAudioFocusChangeListener {
        return AudioManager.OnAudioFocusChangeListener { focusChange ->
            /*
             * Pause playback during alerts and notifications
             */
            when (focusChange) {
                AudioManager.AUDIOFOCUS_LOSS -> {
                    if (BuildConfig.DEBUG) Log.i(TAG, "AUDIOFOCUS_LOSS")
                    // Pause playback
                    changeAudioFocus(false)
                    focusChangedCallback?.onAudioFocusLoss()
                }

                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                    if (BuildConfig.DEBUG) Log.i(TAG, "AUDIOFOCUS_LOSS_TRANSIENT")
                    // Pause playback
                    focusChangedCallback?.onAudioFocusLossTransient()
                }

                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                    if (BuildConfig.DEBUG) Log.i(TAG, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK")
                    // Lower the volume
                    focusChangedCallback?.onAudioFocusLossTransientCanDuck()
                }

                AudioManager.AUDIOFOCUS_GAIN -> {
                    if (BuildConfig.DEBUG) Log.i(TAG, "AUDIOFOCUS_GAIN: ")
                    // Resume playback
                    focusChangedCallback?.onAudioFocusGain()
                }
            }
        }
    }

}