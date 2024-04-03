package com.haotsang.sample.module.rules

import com.haotsang.sample.module.rules.data.ConditionDetail
import org.jeasy.rules.annotation.Action
import org.jeasy.rules.annotation.Condition
import org.jeasy.rules.annotation.Rule
import org.jeasy.rules.api.Facts

@Rule(name = "somewhere rule", description = "fast scene for somewhere rule")
class SomewhereRule {
    private val TAG = "SomewhereRule"

    @Condition
    fun condition(facts: Facts?): Boolean {
        val condition = facts?.get<ConditionDetail>(RuleConstant.SOMEWHERE_CONDITIONS)
        val position = condition?.value?.toInt()
        val fakeData = 0
        return position == fakeData
    }

    @Action
    fun action(facts: Facts?) {

    }
}