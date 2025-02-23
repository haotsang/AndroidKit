package me.haotsang.app.rules_engine.core

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.haotsang.app.rules_engine.core.data.ActionDetail
import me.haotsang.app.rules_engine.core.data.ConditionDetail

object JsonUtil {


    fun conditionToJson(str: String): List<ConditionDetail>{
        return Gson().fromJson(str, object : TypeToken<List<ConditionDetail>>(){}.type)
    }

    fun actionToJson(str: String): List<ActionDetail>{
        return Gson().fromJson(str, object : TypeToken<List<ActionDetail>>(){}.type)
    }
}