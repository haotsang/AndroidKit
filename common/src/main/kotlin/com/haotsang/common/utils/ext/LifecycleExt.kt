package com.haotsang.common.utils.ext

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.collectWhile(
    lifecycleOwner: LifecycleOwner,
    lifecycleState: Lifecycle.State,
    crossinline action: suspend (T) -> Unit
) {
    lifecycleOwner.launchWhile(lifecycleState) {
        collect { action(it) }
    }
}

inline fun <T> Flow<T>.collectLatestWhile(
    lifecycleOwner: LifecycleOwner,
    lifecycleState: Lifecycle.State,
    crossinline action: suspend (T) -> Unit
) {
    lifecycleOwner.launchWhile(lifecycleState) {
        collectLatest { action(it) }
    }
}

/**
 * Launches the block when the target state is reached and cancels it when it is left.
 */
inline fun LifecycleOwner.launchWhile(
    lifecycleState: Lifecycle.State,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    lifecycle.addObserver(object : DefaultLifecycleObserver {
        var job: Job? = null
        override fun onCreate(owner: LifecycleOwner) {
            launchBlockWhen(Lifecycle.State.CREATED)
        }

        override fun onStart(owner: LifecycleOwner) {
            launchBlockWhen(Lifecycle.State.STARTED)
        }

        override fun onResume(owner: LifecycleOwner) {
            launchBlockWhen(Lifecycle.State.RESUMED)
        }

        override fun onPause(owner: LifecycleOwner) {
            cancelWhen(Lifecycle.State.RESUMED)
        }

        override fun onStop(owner: LifecycleOwner) {
            cancelWhen(Lifecycle.State.STARTED)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            cancelWhen(Lifecycle.State.CREATED)
        }

        private fun launchBlockWhen(state: Lifecycle.State) {
            if (lifecycleState == state) {
                job = lifecycleScope.launch { block() }
            }
        }

        private fun cancelWhen(state: Lifecycle.State) {
            if (lifecycleState == state) {
                job?.cancel()
                job = null
            }
        }
    })
}