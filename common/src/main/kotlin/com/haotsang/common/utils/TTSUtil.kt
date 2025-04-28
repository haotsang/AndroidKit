package com.haotsang.common.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeoutOrNull
import java.util.Locale

object TTSUtil {
    private const val TAG = "TTSUtil"
    private val scope = CoroutineScope(Dispatchers.Default)
    private var mTts: TextToSpeech? = null

    private val lock = Mutex()

    fun init(context: Context) {
        if (mTts != null) return

        scope.launch {
            if (mTts != null) return@launch

            lock.withLock(this@TTSUtil) {
                if (mTts != null) return@launch

                Log.i(TAG, "initializing tts")
                val successDeferred: CompletableDeferred<Int> = CompletableDeferred()
                val tts = TextToSpeech(
                    context,
                    { successDeferred.complete(it) },
                    "com.k2fsa.sherpa.onnx.tts.engine"
                )

                val success = withTimeoutOrNull(5000) { successDeferred.await() }
                if (success != TextToSpeech.SUCCESS) {
                    Log.e(TAG, "failed to initialize tts, result = $success")
                    return@launch
                }

                val result = tts.setLanguage(Locale.getDefault())
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e(TAG, "language ${Locale.getDefault()} is not supported")
                    tts.shutdown()
                    return@launch
                }

                mTts = tts
                Log.i(TAG, "tts engine is initialized")
            }
        }
    }

    fun close() {
        mTts?.shutdown()
    }

    fun speak(content: String) {
        if (mTts == null) {
            Log.d(TAG, "tts not initialized")
            return
        }

        val result = mTts?.speak(content, TextToSpeech.QUEUE_FLUSH, null, null)
        if (result != TextToSpeech.SUCCESS) {
            Log.d(TAG, "failed to speak $result")
        }
    }

    fun stopCurrent() {
        mTts?.stop()
    }
}