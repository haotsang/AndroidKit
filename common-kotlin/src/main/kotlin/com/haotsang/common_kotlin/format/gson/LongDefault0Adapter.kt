package com.haotsang.common_kotlin.format.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class LongDefault0Adapter : JsonDeserializer<Long>, JsonSerializer<Long> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Long {
        if (json?.asString.equals("")) {
            return 0L
        }
        return try {
            json!!.asLong
        } catch (e: NumberFormatException) {
            0L
        }
    }

    override fun serialize(src: Long?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src)
    }
}
