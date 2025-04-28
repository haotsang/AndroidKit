/*
 * Dr. Ing. h.c. F. Porsche AG confidential. This code is protected by intellectual property rights.
 * The Dr. Ing. h.c. F. Porsche AG owns exclusive legal rights of use.
 */
package com.haotsang.common.utils

import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class ShowToast(
    val text: String,
    val duration: Long,
)

fun showToast(
    text: String,
    duration: Int = Toast.LENGTH_SHORT,
) {
    val durationMills = when (duration) {
        Toast.LENGTH_SHORT -> 2000L
        else -> 3500L
    }
    ToastService.send(ShowToast(text, durationMills))
}

object ToastService {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val shownToastFlow: MutableStateFlow<ShowToast?> = MutableStateFlow(null)

    private val inbox = MutableSharedFlow<ShowToast>(
        replay = 0,
        extraBufferCapacity = TOAST_BUFFER_CAPACITY,
        onBufferOverflow = DROP_OLDEST,
    )

    init {
        scope.launch {
            inbox.collect { toast ->
                if (shownToastFlow.value == null) {
                    // wait for previous toast to animate out
                    delay(100)
                }
                showToast(toast.text, toast.duration)
            }
        }
    }

    fun send(show: ShowToast) {
        inbox.tryEmit(show)
    }

    private suspend fun showToast(text: String, duration: Long) {
        shownToastFlow.value = ShowToast(text, duration)
        delay(duration)
        shownToastFlow.value = null
    }
}

private const val TOAST_BUFFER_CAPACITY = 3