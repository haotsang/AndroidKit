package com.haotsang.sample.rules

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.haotsang.sample.rules.data.ActionDetail
import com.haotsang.sample.rules.data.ConditionDetail

object JsonUtil {


    fun conditionToJson(str: String): List<ConditionDetail>{
        return Gson().fromJson(str, object : TypeToken<List<ConditionDetail>>(){}.type)
    }

    fun actionToJson(str: String): List<ActionDetail>{
        return Gson().fromJson(str, object : TypeToken<List<ActionDetail>>(){}.type)
    }
}