package com.haotsang.deps

object Deps {

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"

    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activity}"
    const val annotations = "androidx.annotation:annotation:${Versions.annotations}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"

    const val core = "androidx.core:core:${Versions.core}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.core}"

    //View
    const val cardView = "androidx.cardview:cardview:${Versions.cardView}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:${Versions.coordinatorLayout}"
    const val customView = "androidx.customview:customview:${Versions.customView}"
    const val drawerLayout = "androidx.drawerlayout:drawerlayout:${Versions.drawerLayout}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val recyclerviewSelection = "androidx.recyclerview:recyclerview-selection:${Versions.recyclerviewSelection}}"
    const val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.viewpager2}"
    const val asyncLayoutInflater = "androidx.asynclayoutinflater:asynclayoutinflater:${Versions.asyncLayoutInflater}"

    const val archCoreRuntime = "androidx.arch.core:core-runtime:${Versions.archCore}"
    const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archCore}"

    const val arouterApi = "com.alibaba:arouter-api:${Versions.arouterApi}"
    const val arouterCompiler = "com.alibaba:arouter-compiler:${Versions.arouterCompiler}"
    const val arouterRegister = "com.alibaba:arouter-register:${Versions.arouterRegister}"

    const val atslCore = "androidx.test:core:${Versions.atslCore}"
    const val atslExtJunit = "androidx.test.ext:junit:${Versions.atslJunit}"
    const val atslRunner = "androidx.test:runner:${Versions.atslRunner}"
    const val atslRules = "androidx.test:rules:${Versions.atslRules}"
    const val androidxDataBinding = "androidx.databinding:viewbinding:${Versions.androidxDataBinding}"
    const val benchmark = "androidx.benchmark:benchmark-junit4:${Versions.benchmark}"
    const val benchmark_gradle = "androidx.benchmark:benchmark-gradle-plugin:${Versions.benchmark}"
    const val biometric = "androidx.biometric:biometric:${Versions.biometric}"
    const val browser = "androidx.browser:browser:${Versions.browser}"

    //cameraX
    const val cameraXCamera2 = "androidx.camera:camera-camera2:${Versions.cameraX}"
    const val cameraXCore = "androidx.camera:camera-core:${Versions.cameraX}"
    const val cameraXLifecycle = "androidx.camera:camera-lifecycle:${Versions.cameraX}"
    const val cameraXView = "androidx.camera:camera-view:${Versions.cameraX}"
    const val cameraXExtensions = "androidx.camera:camera-extensions:${Versions.cameraXExtensions}"


    //coroutines
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutinesRx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:${Versions.coroutines}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

    //datastore
    const val datastoreCore = "androidx.datastore:datastore:${Versions.datastore}"
    const val datastoreRx2 = "androidx.datastore:datastore-rxjava2:${Versions.datastore}"
    const val datastoreRx3 = "androidx.datastore:datastore-rxjava3:${Versions.datastore}"
    const val datastorePreferences = "androidx.datastore:datastore-preferences:${Versions.datastore}"
    const val datastorePreferencesRx2 = "androidx.datastore:datastore-preferences-rxjava2:${Versions.datastore}"
    const val datastorePreferencesRx3 = "androidx.datastore:datastore-preferences-rxjava3:${Versions.datastore}"

    const val dexMaker = "com.linkedin.dexmaker:dexmaker-mockito:${Versions.dexMaker}"

    //exoplayer
    const val exoplayer = "com.google.android.exoplayer:exoplayer:${Versions.exoplayerVersion}"
    const val exoplayerCore = "com.google.android.exoplayer:exoplayer-core:${Versions.exoplayerVersion}"
    const val exoplayerUI = "com.google.android.exoplayer:exoplayer-ui:${Versions.exoplayerVersion}"
    const val exoplayerHls = "com.google.android.exoplayer:exoplayer-hls:${Versions.exoplayerVersion}"
    const val exoplayerDash = "com.google.android.exoplayer:exoplayer-dash:${Versions.exoplayerVersion}"
    const val exoplayerSmooth = "com.google.android.exoplayer:exoplayer-smoothstreaming:${Versions.exoplayerVersion}"
    const val exoplayerTransformer = "com.google.android.exoplayer:exoplayer-transformer:${Versions.exoplayerVersion}"
    const val exoplayerRtmp = "com.google.android.exoplayer:extension-rtmp:${Versions.exoplayerVersion}"
    const val exoplayerRtsp = "com.google.android.exoplayer:extension-rtsp:${Versions.exoplayerVersion}"
    const val exoplayerMediaSession = "com.google.android.exoplayer:extension-mediasession:${Versions.exoplayerVersion}"
    const val exoplayerCast = "com.google.android.exoplayer:extension-cast:${Versions.exoplayerVersion}"



    const val media3Exoplayer = "androidx.media3:media3-exoplayer:${Versions.media3}"
    const val media3ExoplayerDash = "androidx.media3:media3-exoplayer-dash:${Versions.media3}"
    const val media3ExoplayerHls = "androidx.media3:media3-exoplayer-hls:${Versions.media3}"
    const val media3ExoplayerRtsp = "androidx.media3:media3-exoplayer-rtsp:${Versions.media3}"
    const val media3Cronet = "androidx.media3:media3-datasource-cronet:${Versions.media3}"
    const val media3Okhttp = "androidx.media3:media3-datasource-okhttp:${Versions.media3}"
    const val media3Rtmp = "androidx.media3:media3-datasource-rtmp:${Versions.media3}"
    const val media3Ui = "androidx.media3:media3-ui:${Versions.media3}"
    const val media3Session = "androidx.media3:media3-session:${Versions.media3}"
    const val media3Extractor = "androidx.media3:media3-extractor:${Versions.media3}"

    // Common functionality for media database components
    const val media3Database = "androidx.media3:media3-database:${Versions.media3}"

    // Common functionality for media decoders
    const val media3Decoder = "androidx.media3:media3-decoder:${Versions.media3}"

    // Common functionality for loading data
    const val media3Datasource = "androidx.media3:media3-datasource:${Versions.media3}"

    // Common functionality used across multiple media libraries
    const val media3Common = "androidx.media3:media3-common:${Versions.media3}"

    const val multidex = "androidx.multidex:multidex:2.0.1"

    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    const val espressoIntents = "androidx.test.espresso:espresso-intents:${Versions.espresso}"

    const val fragmentRuntime = "androidx.fragment:fragment:${Versions.fragment}"
    const val fragmentRuntimeKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.fragment}"

    const val glideRuntime = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val glideIntegration = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"

    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val hamcrest = "org.hamcrest:hamcrest-all:${Versions.hamcrest}"

    const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val hiltRuntime = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltAndroidCompile = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    const val androidXHiltWork = "androidx.hilt:hilt-work:${Versions.androidXHilt}"
    const val androidXHiltCompile = "androidx.hilt:hilt-compiler:${Versions.androidXHilt}"

    const val jacoco = "org.jacoco:org.jacoco.core:${Versions.jacoco}"
    const val junit = "junit:junit:${Versions.junit}"

    const val kotlinStdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlinTest = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlinAllOpen = "org.jetbrains.kotlin:kotlin-allopen:${Versions.kotlin}"

    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleLivedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel:${Versions.lifecycle}"
    const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata:${Versions.lifecycle}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle}"
    const val lifecycleViewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    const val lifecycleCompilerJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val lifecycleService = "androidx.lifecycle:lifecycle-service:${Versions.lifecycle}"
    const val lifecycleProcess = "androidx.lifecycle:lifecycle-process:${Versions.lifecycle}"
    const val lifecycleReactivestreams = "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.lifecycle}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensions}"

    const val material = "com.google.android.material:material:${Versions.material}"

    const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockito}"
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockitoAll = "org.mockito:mockito-all:${Versions.mockitoAll}"
    const val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockitoAndroid}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}"

    const val navigationRuntime = "androidx.navigation:navigation-runtime:${Versions.navigation}"
    const val navigationRuntimeKtx = "androidx.navigation:navigation-runtime-ktx:${Versions.navigation}"
    const val navigationFragment = "androidx.navigation:navigation-fragment:${Versions.navigation}"
    const val navigationTesting = "androidx.navigation:navigation-testing:${Versions.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui:${Versions.navigation}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val navigationSafeArgsPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"

    const val okhttpRuntime = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val okio = "com.squareup.okio:okio:1.17.5"

    const val pagingRuntime = "androidx.paging:paging-runtime-ktx:${Versions.paging}"
    const val pagingCommon = "androidx.paging:paging-common-ktx:${Versions.paging}"
    const val pagingRxjava = "androidx.paging:paging-rxjava2-ktx:${Versions.paging}"

    const val picasso = "com.squareup.picasso:picasso:2.8"
    const val preference = "androidx.preference:preference-ktx:1.2.0"

    const val retrofitRuntime = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofitRxjava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val retrofitMock = "com.squareup.retrofit2:retrofit-mock:${Versions.retrofit}"

    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomRxjava2 = "androidx.room:room-rxjava2:${Versions.room}"
    const val roomTesting = "androidx.room:room-testing:${Versions.room}"

    const val slice = "androidx.slice:slice-builders:${Versions.slice}"

    const val workRuntime = "androidx.work:work-runtime:${Versions.work}"
    const val workRuntimeKtx = "androidx.work:work-runtime-ktx:${Versions.work}"
    const val workRxjava2 = "androidx.work:work-rxjava2:${Versions.work}"
    const val workMultiProcess = "androidx.work:work-multiprocess:${Versions.work}"
    const val workTesting = "androidx.work:work-testing:${Versions.work}"

    const val webkit = "androidx.webkit:webkit:${Versions.webkit}"

    const val zxing = "com.google.zxing:core:${Versions.zxing}"
    const val zxingJavaSe = "com.google.zxing:javase:${Versions.zxing}"

    const val rxjava2 = "io.reactivex.rxjava2:rxjava:${Versions.rxJava2}"
    const val rxAndroid2 = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid2}"
    const val rxjava3 = "io.reactivex.rxjava3:rxjava:${Versions.rxJava3}"
    const val rxAndroid3 = "io.reactivex.rxjava3:rxandroid:${Versions.rxAndroid3}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val transition = "androidx.transition:transition:${Versions.transition}"
    const val truth = "com.google.truth:truth:${Versions.truth}"


    const val javaJwt = "com.auth0:java-jwt:${Versions.javaJwt}"
    const val androidJwtDecode = "com.auth0.android:jwtdecode:2.0.2"

    const val leakcanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"

    const val mmkv = "com.tencent:mmkv:${Versions.mmkv}"

    const val liulishuo = "com.liulishuo.okdownload:okdownload:${Versions.liulishuo}" //核心库
    const val liulishuoSql = "com.liulishuo.okdownload:sqlite:${Versions.liulishuo}" //存储断点信息的数据库
    const val liulishuoOkhttp = "com.liulishuo.okdownload:okhttp:${Versions.liulishuo}" //提供okhttp连接，如果使用的话，需要引入okhttp网络请求库



}