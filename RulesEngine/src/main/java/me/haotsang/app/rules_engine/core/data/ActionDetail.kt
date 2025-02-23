package me.haotsang.app.rules_engine.core.data

data class ActionDetail (
    var actionName: String,
    var actionCode: ActionTypeEnum,
    var value: String,
    var desc: String,
    var address: String?,
    var showText: String?
)