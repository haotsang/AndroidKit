package com.haotsang.sample.di

import javax.inject.Inject


class DataCreator @Inject constructor() {

    var name = "hello world"
    override fun toString(): String {
        return "DataCreator{" +
                "name='" + name + '\'' +
                '}'
    }
}