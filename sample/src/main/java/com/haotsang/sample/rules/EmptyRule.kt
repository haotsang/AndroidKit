package com.haotsang.sample.rules

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Message
import android.util.Log
import com.google.gson.Gson
import com.haotsang.sample.rules.data.Instruct
import org.jeasy.rules.annotation.Action
import org.jeasy.rules.annotation.Condition
import org.jeasy.rules.annotation.Rule
import org.jeasy.rules.api.Facts

@Rule(name = "empty rule", description = "fast scene for empty rule")
internal class EmptyRule(activity: Context) {
    val actionHandler by lazy { ActionHandler(activity) }

    @Condition
    fun condition(facts: Facts?): Boolean {
        return true
    }

    @SuppressLint("StringFormatMatches")
    @Action
    fun action(facts: Facts?) {
        val instruct = facts?.get<Instruct>(RuleConstant.SCENE_DATA)
        Log.d(
            "test",
            "already executed instruct-->" + Gson().toJson(RuleConstant.executedIds)
        )
        Log.d("test", " instruct-->" + Gson().toJson(instruct))
        instruct?.let {
            if (!RuleConstant.executedIds.add(it.id)) {
                return
            }
        }
        Log.d("test", "执行指令-->" + Gson().toJson(instruct))
        val actionInfos = facts?.get<String>(RuleConstant.ACTIONS)
        val name = facts?.get<String>(RuleConstant.SCENE_NAME)
        val bundle = Bundle()
        bundle.putString(RuleConstant.ACTIONS, actionInfos)
        bundle.putString(RuleConstant.SCENE_NAME, name)
        val message = Message()
        message.data = bundle
        actionHandler.sendMessage(message)
    }
}