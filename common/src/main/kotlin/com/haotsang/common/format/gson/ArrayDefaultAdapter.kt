package com.haotsang.common.format.gson

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.Collections

class ArrayDefaultAdapter : JsonDeserializer<List<*>> {

    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): List<*> {
        return if (json.isJsonArray) {
            val newGson = Gson()
            newGson.fromJson(json, typeOfT)
        } else {
            Collections.EMPTY_LIST
        }
    }
}