package com.haotsang.sample.di

import android.content.res.Resources
import javax.inject.Inject


class DataCreator @Inject constructor(
    resources: Resources
) {

    var name = "hello world"
    override fun toString(): String {
        return "DataCreator{" +
                "name='" + name + '\'' +
                '}'
    }
}