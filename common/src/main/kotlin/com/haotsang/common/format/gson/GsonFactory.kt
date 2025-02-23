package com.haotsang.common.format.gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class GsonFactory private constructor() {

    companion object {
        val instance: GsonFactory by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            GsonFactory()
        }
    }

    /**
     * 获取单例的 Gson 对象
     */
    fun getSingletonGson(): Gson {
        return instance.newGsonBuilder().create()
    }

    /**
     * 创建 Gson 构建对象
     */
    private fun newGsonBuilder(): GsonBuilder {
        return GsonBuilder()
            .registerTypeAdapter(Int::class.java, IntDefault0Adapter())
            .registerTypeAdapter(Double::class.java, DoubleDefault0Adapter())
            .registerTypeAdapter(Long::class.java, LongDefault0Adapter())
            .registerTypeAdapter(List::class.java, ArrayDefaultAdapter())
    }
}