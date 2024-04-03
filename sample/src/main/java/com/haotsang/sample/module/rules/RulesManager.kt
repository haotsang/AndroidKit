package com.haotsang.sample.module.rules

import android.content.Context
import android.os.Bundle
import android.os.Message
import com.haotsang.sample.module.rules.data.ConditionDetail
import com.haotsang.sample.module.rules.data.ConditionTypeEnum
import com.haotsang.sample.module.rules.data.Instruct
import org.jeasy.rules.api.Facts
import org.jeasy.rules.api.Rule
import org.jeasy.rules.api.Rules
import org.jeasy.rules.api.RulesEngine
import org.jeasy.rules.core.DefaultRulesEngine
import org.jeasy.rules.support.composite.UnitRuleGroup

object RulesManager {

    private const val TAG = "RulesManager"

    fun addRules(activity: Context, instruct: Instruct, auto: Boolean) {
        val facts = Facts()
        val unitRulesGroup = object : UnitRuleGroup() {
            var rules: Set<Rule>
                get() = rules
                set(rules) {
                    super.rules = rules
                }
        }
        var conditionList = emptyList<ConditionDetail>()
        if (auto) {
            conditionList = JsonUtil.conditionToJson(instruct.condition_value)
        }

        for (condition in conditionList.iterator()) {
            when (condition.conditionCode) {
                ConditionTypeEnum.timeFrame -> {
                    unitRulesGroup.addRule(TimeConditionRule())
                    facts.put(RuleConstant.TIMEFRAME_CONDITIONS, condition)
                }
                ConditionTypeEnum.somewhere -> {
                    unitRulesGroup.addRule(SomewhereRule())
                    facts.put(RuleConstant.SOMEWHERE_CONDITIONS, condition)
                }
                else -> {}
            }
        }

        if (unitRulesGroup.rules.isNotEmpty()) {
            unitRulesGroup.addRule(EmptyRule(activity))
            facts.put(RuleConstant.ACTIONS, instruct.action_value)
            facts.put(RuleConstant.SCENE_DATA, instruct)
            facts.put(RuleConstant.SCENE_NAME, instruct.title)
            val rules = Rules(unitRulesGroup)
            val rulesEngine: RulesEngine = DefaultRulesEngine()
            rulesEngine.fire(rules, facts)
        } else {
            val actionHandler by lazy { ActionHandler(activity) }
            val bundle = Bundle()
            bundle.putString(RuleConstant.ACTIONS, instruct.action_value)
            bundle.putString(RuleConstant.SCENE_NAME, instruct.title)
            val message = Message()
            message.data = bundle
            actionHandler.sendMessage(message)
        }
    }
}