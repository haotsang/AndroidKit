package me.haotsang.app.rules_engine.core

import android.util.Log
import me.haotsang.app.rules_engine.core.RuleConstant
import me.haotsang.app.rules_engine.core.data.ConditionDetail
import org.jeasy.rules.annotation.Action
import org.jeasy.rules.annotation.Condition
import org.jeasy.rules.annotation.Rule
import org.jeasy.rules.api.Facts
import java.time.LocalTime


@Rule(name = "time rule", description = "fast scene for time rule")
class TimeConditionRule {
    private val TAG = "TimeRule"

    @Condition
    fun condition(facts: Facts?): Boolean{
        val timeCondition = facts?.get<ConditionDetail>(RuleConstant.TIMEFRAME_CONDITIONS)
        val time = timeCondition?.value?.split("-")
        val startTimes = time?.get(0)?.split(":")
        val endTimes = time?.get(1)?.split(":")
        val startTime = startTimes?.let {
            LocalTime.of(it[0].toInt(), it[1].toInt())
        }
        val endTime = endTimes?.let {
            LocalTime.of(it[0].toInt(), it[1].toInt())
        }
        val isBetween = isBetween(startTime, endTime)
        Log.i(TAG, "startime:$startTime endTime:$endTime isBetween:$isBetween")
        return isBetween
    }

    @Action
    fun action(facts: Facts?) {

    }

    private fun isBetween(beginTime: LocalTime?, endTime: LocalTime?):Boolean{
        val now = LocalTime.now()
        var flag = false
        if (now.isAfter(beginTime) && now.isBefore(endTime)){
            flag = true
        }
        return flag
    }
}