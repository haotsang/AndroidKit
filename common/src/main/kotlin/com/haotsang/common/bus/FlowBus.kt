package com.haotsang.common.bus

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/*
// 主线程-发送消息
FlowBus.with<String>("test-key-01").post(this@Demo11OneFragment2.lifecycleScope, "Test Flow Bus Message")

// 接收消息
FlowBus.with<String>("test-key-01").register(this) {
    LogUtils.w("收到FlowBus消息 - " + it)
}

//发送粘性消息
FlowBus.withStick<String>("test-key-02").post(lifecycleScope, "Test Stick Message")

FlowBus.withStick<String>("test-key-02").register(this){
    LogUtils.w("收到粘性消息：$it")
}
*/


object FlowBus {
    private val busMap = mutableMapOf<String, EventBus<*>>()
    private val busStickMap = mutableMapOf<String, StickEventBus<*>>()

    @Synchronized
    fun <T> with(key: String): EventBus<T> {
        var eventBus = busMap[key]
        if (eventBus == null) {
            eventBus = EventBus<T>(key)
            busMap[key] = eventBus
        }
        return eventBus as EventBus<T>
    }

    @Synchronized
    fun <T> withStick(key: String): StickEventBus<T> {
        var eventBus = busStickMap[key]
        if (eventBus == null) {
            eventBus = StickEventBus<T>(key)
            busStickMap[key] = eventBus
        }
        return eventBus as StickEventBus<T>
    }

    //真正实现类
    open class EventBus<T>(private val key: String) : LifecycleObserver {

        //私有对象用于发送消息
        private val _events: MutableSharedFlow<T> by lazy {
            obtainEvent()
        }

        //暴露的公有对象用于接收消息
        val events = _events.asSharedFlow()

        open fun obtainEvent(): MutableSharedFlow<T> = MutableSharedFlow(0, 1, BufferOverflow.DROP_OLDEST)

        //主线程接收数据
        fun register(lifecycleOwner: LifecycleOwner, action: (t: T) -> Unit) {
            lifecycleOwner.lifecycle.addObserver(this)
            lifecycleOwner.lifecycleScope.launch {
                events.collect {
                    try {
                        action(it)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.w("","FlowBus - Error:$e")
                    }
                }
            }
        }

        //协程中发送数据
        suspend fun post(event: T) {
            _events.emit(event)
        }

        //主线程发送数据
        fun post(scope: CoroutineScope, event: T) {
            scope.launch {
                _events.emit(event)
            }
        }

        //自动销毁
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            Log.w("","FlowBus - 自动onDestroy")
            val subscriptCount = _events.subscriptionCount.value
            if (subscriptCount <= 0)
                busMap.remove(key)
        }
    }

    class StickEventBus<T>(key: String) : EventBus<T>(key) {
        override fun obtainEvent(): MutableSharedFlow<T> = MutableSharedFlow(1, 1, BufferOverflow.DROP_OLDEST)
    }

}