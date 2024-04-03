package com.haotsang.deps

import org.gradle.api.JavaVersion

object BuildVersion {
    const val minSdk = 21
    const val compileSdk = 34
    const val targetSdk = 34
    val javaVersion = JavaVersion.VERSION_17
}
