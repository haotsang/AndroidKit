package me.haotsang.app.rules_engine.core.data


enum class ActionTypeEnum {
    SWITCH,

    SINGLE_LIGHT,

    DUAL_LIGHT,

    FAN,

    CURTAIN
}

enum class ConditionTypeEnum{
    timeFrame,
    somewhere
}