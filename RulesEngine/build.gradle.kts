plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "me.haotsang.app.rules_engine"
    compileSdk = providers.gradleProperty("compileSdk").get().toInt()

    defaultConfig {
        applicationId = "me.haotsang.app.rules_engine"
        minSdk = providers.gradleProperty("minSdk").get().toInt()
        targetSdk = providers.gradleProperty("targetSdk").get().toInt()
        versionCode = providers.gradleProperty("VERSIONCODE").get().toInt()
        versionName = providers.gradleProperty("VERSIONNAME").get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    //规则引擎
    implementation(libs.easy.rules.core)
    implementation(libs.easy.rules.support)
    //gson
    implementation(libs.gson)

    // androidx
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}