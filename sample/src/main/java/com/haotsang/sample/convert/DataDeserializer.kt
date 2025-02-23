package com.haotsang.sample.convert

import android.content.Context
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.haotsang.sample.ktx.getString
import java.lang.reflect.Type

class DataDeserializer constructor(private val context: Context) :
    JsonDeserializer<Any> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Any? {
        return json?.asJsonObject?.run {
            if (getString("key") == "key") {
                generateMediaItem(this)
            } else {
                null
            }
        }

    }

    private fun generateMediaItem(jsonObject: JsonObject): Any? {
        return null
    }

}