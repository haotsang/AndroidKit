package com.haotsang.common.format.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class IntDefault0Adapter : JsonDeserializer<Int>, JsonSerializer<Int> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Int {
        if (json?.asString.equals("")) {
            return 0
        }
        return try {
            json!!.asInt
        } catch (e: NumberFormatException) {
            0
        }
    }

    override fun serialize(src: Int?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src)
    }
}

