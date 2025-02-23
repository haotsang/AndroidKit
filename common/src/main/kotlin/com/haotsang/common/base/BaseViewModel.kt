package com.haotsang.common.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


typealias Block<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (Throwable) -> Unit


sealed class StatusData<out T> {

    data class Success<out T>(val data: T) : StatusData<T>()

    data class Error(val exception: Throwable) : StatusData<Nothing>()

    data object Loading : StatusData<Nothing>()

    data object Empty : StatusData<Nothing>()

    data object Idle : StatusData<Nothing>()

    fun isSuccess(): Boolean = this is Success
}


abstract class BaseViewModel : ViewModel() {

    protected fun launch(
        block: Block<Unit>,
        error: Error? = null,
    ): Job {
        return viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                // 使用日志记录异常，以便于问题诊断
                Log.e("LaunchWithErrorHandling", "Exception caught in coroutine", e)
                // 调用错误处理回调，确保耗时操作不会在主线程执行
                error?.invoke(e)
            }
        }
    }
}