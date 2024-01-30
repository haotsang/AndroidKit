package com.haotsang.common_kotlin.http

import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor

class RetrofitUtils {

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message: String ->
            Platform.get().log(message)
        }.setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}