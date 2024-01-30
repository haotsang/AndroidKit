package com.haotsang.sample.rules.data

data class Instruct (
    var id: Long,
    var title: String,
    var condition_value: String,
    var action_value: String,
    var auto_execute: Boolean,
)