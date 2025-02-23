package com.haotsang.common.utils

import java.net.HttpRetryException
import java.net.HttpURLConnection
import java.net.URL

class NetworkUtils {

    companion object {
        //200 OK
        const val OK = 200
        //206 Partial Content
        const val Partial = 206
    }
    private fun connect(url: String, headers: Map<String, String> = mapOf()): HttpURLConnection {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.setRequestProperty("Accept", "application/*")
        headers.forEach {
            connection.setRequestProperty(it.key, it.value)
        }
        connection.connectTimeout = 10000
        connection.connect()

        if (connection.responseCode != OK && connection.responseCode != Partial) {
            throw HttpRetryException(connection.responseMessage, connection.responseCode)
        }
        return connection
    }
}