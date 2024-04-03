package com.haotsang.sample.module.rules.data

data class ConditionDetail (
    var conditionName: String,
    var conditionCode: ConditionTypeEnum,
    var paramValue: String,
    var value: String,
    var desc: String
)