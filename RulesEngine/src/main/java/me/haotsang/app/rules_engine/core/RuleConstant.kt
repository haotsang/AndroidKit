package me.haotsang.app.rules_engine.core

import java.util.Collections

object RuleConstant {
    val executedIds = Collections.synchronizedSet(mutableSetOf<Long>())
    /**
     * Rule 使用
     * 动作 actions
     */
    const val ACTIONS = "ACTIONS"
    const val SCENE_DATA = "sceneData"
    const val SCENE_NAME = "sceneName"

    /**
     * 条件
     */
    const val TIMEFRAME_CONDITIONS = "timeFrame_conditions"
    const val SOMEWHERE_CONDITIONS = "somewhere_conditions"

}