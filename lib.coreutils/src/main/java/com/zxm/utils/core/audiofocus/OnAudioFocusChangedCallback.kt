package com.zxm.utils.core.audiofocus

import android.media.AudioManager

/**
 * Created by zhangxinmin on 2023/12/14.
 * Interface definition for a callback to be invoked when the audio focus of the system is updated.
 * @see AudioManager.OnAudioFocusChangeListener
 */

interface OnAudioFocusChangedCallback {
    /**
     * Called on the listener to notify it the audio focus for this listener has been changed.
     *
     * You can pause playback.
     *
     * @see AudioManager.AUDIOFOCUS_LOSS
     */
    fun onAudioFocusLoss()

    /**
     * Called on the listener to notify it the audio focus for this listener has been changed.
     * Indicate a temporary gain or request of audio focus, anticipated to last a short amount of time.
     *
     * You can pause playback.
     *
     * @see AudioManager.AUDIOFOCUS_LOSS_TRANSIENT
     */
    fun onAudioFocusLossTransient()

    /**
     * Called on the listener to notify it the audio focus for this listener has been changed.
     * Indicate a temporary request of audio focus, anticipated to last a short amount of time,
     * and where it is acceptable for other audio applications to keep playing after having lowered
     * their output level (also referred to as "ducking").
     *
     * You can lower your volume.
     *
     * @see AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK
     */
    fun onAudioFocusLossTransientCanDuck()

    /**
     * Called on the listener to notify it the audio focus for this listener has been changed.
     * Indicate a gain of audio focus.
     *
     * You can resume playback.
     *
     * @see AudioManager.AUDIOFOCUS_GAIN
     */
    fun onAudioFocusGain()
}