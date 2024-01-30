package com.haotsang.common_kotlin.manager

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import android.util.Log

/**
 *    implements AudioFocusManager.AudioFocusListener
 *
 *    private val audioFocusManager: AudioFocusManager by lazy {
 *         AudioFocusManager(context, this)
 *     }
 *
 */
class AudioFocusManager(
    private val context: Context,
    private val audioFocusListener: AudioFocusListener
) {

    companion object {
        const val TAG = "AudioFocusManager"
    }

    interface AudioFocusListener {
        fun onPlayMedia()
        fun onPauseMedia()
    }

    private var mCanResumeWhenAudioFocusGain = true

    private var mFocusState =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) AudioManager.AUDIOFOCUS_NONE else AudioManager.AUDIOFOCUS_REQUEST_FAILED

    private var mAudioFocusRequest: AudioFocusRequest? = null


    private val mAudioManager: AudioManager =
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager


    private val audioAttributes: AudioAttributes by lazy {
        AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setLegacyStreamType(AudioManager.STREAM_MUSIC)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
    }

    private val audioFocusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        Log.d(TAG, "focusChange: $focusChange")
        mFocusState = focusChange

        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT -> {
                if (mCanResumeWhenAudioFocusGain) {
                    audioFocusListener.onPlayMedia()
                }
            }

            AudioManager.AUDIOFOCUS_NONE, AudioManager.AUDIOFOCUS_LOSS -> {
                mCanResumeWhenAudioFocusGain = false
                audioFocusListener.onPauseMedia()
            }

            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                mCanResumeWhenAudioFocusGain = true
                audioFocusListener.onPauseMedia()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                //暂时失去了音频焦点，但可以小声地继续播放音频
            }
        }
    }


    fun requestAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mAudioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(audioAttributes)
                .setAcceptsDelayedFocusGain(true)
                .setOnAudioFocusChangeListener(audioFocusChangeListener)
                .build()

            mFocusState = mAudioFocusRequest?.let { mAudioManager.requestAudioFocus(it) }
                ?: AudioManager.AUDIOFOCUS_NONE
        } else {
            @Suppress("DEPRECATION")
            mFocusState = mAudioManager.requestAudioFocus(
                audioFocusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            )
        }

        Log.d(TAG, "requestAudioFocus result: $mFocusState")
    }


    fun hasFocus(): Boolean {
        return (mFocusState == AudioManager.AUDIOFOCUS_GAIN)
                || (mFocusState == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                || (mFocusState == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
                || (mFocusState == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE)
    }

    fun abandonAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mAudioFocusRequest?.let {
                mFocusState = mAudioManager.abandonAudioFocusRequest(it)
            }
        } else {
            @Suppress("DEPRECATION")
            mFocusState = mAudioManager.abandonAudioFocus(audioFocusChangeListener)
        }

        Log.d(TAG, "abandonAudioFocus result: $mFocusState")
    }

}