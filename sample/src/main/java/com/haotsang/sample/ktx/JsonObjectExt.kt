package com.haotsang.sample.ktx

import com.google.gson.JsonArray
import com.google.gson.JsonObject

fun JsonObject.getInt(key: String): Int {
    if (has(key)) {
        get(key).let {
            if (it.isJsonPrimitive) {
                if (it.asJsonPrimitive.isNumber) {
                    return it.asJsonPrimitive.asNumber.toInt()
                }
            }
        }
    }
    return 0
}

fun JsonObject.getLong(key: String): Long {
    if (has(key)) {
        get(key).let {
            if (it.isJsonPrimitive) {
                if (it.asJsonPrimitive.isNumber) {
                    return it.asJsonPrimitive.asNumber.toLong()
                }
            }
        }
    }
    return 0
}

fun JsonObject.getDouble(key: String): Double {
    if (has(key)) {
        get(key).let {
            if (it.isJsonPrimitive) {
                if (it.asJsonPrimitive.isNumber) {
                    return it.asJsonPrimitive.asNumber.toDouble()
                }
            }
        }
    }
    return 0.0
}

fun JsonObject.getString(key: String): String? {
    if (has(key)) {
        get(key).let {
            if (it.isJsonPrimitive) {
                if (it.asJsonPrimitive.isString) {
                    return it.asJsonPrimitive.asString
                }
            }
        }
    }
    return null
}

fun JsonObject.getBoolean(key: String): Boolean {
    if (has(key)) {
        get(key).let {
            if (it.isJsonPrimitive) {
                if (it.asJsonPrimitive.isBoolean) {
                    return it.asJsonPrimitive.asBoolean
                }
            }
        }
    }
    return false
}

fun JsonObject.getJsonObject(key: String): JsonObject? {
    if (has(key)) {
        get(key).let {
            if (it.isJsonObject) {
                return it.asJsonObject
            }
        }
    }
    return null
}

fun JsonObject.getJsonArray(key: String): JsonArray? {
    if (has(key)) {
        get(key).let {
            if (it.isJsonArray) {
                return it.asJsonArray
            }
        }
    }
    return null
}
