package com.github.pedrodimoura.news

object Dependencies {
    @JvmStatic
    val app = arrayListOf(
        Libraries.kotlinStdLib,
        Libraries.appCompat,
        Libraries.coreKtx,
        Libraries.constraintLayout,
        Libraries.material,
        Libraries.coroutinesCore,
        Libraries.coroutinesAndroid,
        Libraries.koinCore,
        Libraries.koinExt,
        Libraries.koinAndroid,
        Libraries.koinScope,
        Libraries.koinViewModel,
        Libraries.picasso,
        Libraries.retrofit,
        Libraries.okHttp,
        Libraries.httpLoggingInterceptor,
        Libraries.moshiConverterAdapter,
        Libraries.timber,
        Libraries.lifecycleExtensions,
        Libraries.moshi,
        Libraries.moshiAdapters,
        Libraries.moshiKotlin,
        Libraries.roomRuntime,
        Libraries.roomExtensions,
        Libraries.paging,
        Libraries.glide,
        Libraries.flexBoxLayout
    )

    @JvmStatic
    val appKapt = arrayListOf(
        Libraries.lifecycleCompiler,
        Libraries.roomCompiler,
        Libraries.glideCompiler
    )

    @JvmStatic
    val test = arrayListOf(
        Libraries.koinTest,
        Libraries.mockitoCore,
        Libraries.mockitoInline,
        Libraries.mockWebServer,
        Libraries.junit,
        Libraries.lifecycleTest,
        Libraries.roomTesting
    )

    @JvmStatic
    val instrumented = arrayListOf(
        Libraries.runner,
        Libraries.espressoCore
    )
}

private object Versions {
    const val kotlin = "1.3.41"
    const val coreKtx = "1.0.2"
    const val appCompat = "1.0.2"
    const val constraintLayout = "1.1.3"
    const val material = "1.0.0"
    const val junit = "4.12"
    const val runner = "1.2.0"
    const val espressoCore = "3.2.0"
    const val coroutines = "1.3.0-M2"
    const val mockito = "2.23.4"
    const val koin = "2.0.1"
    const val mockWebServer = "4.1.0"
    const val picasso = "2.71828"
    const val retrofit = "2.6.1"
    const val okHttp = "4.1.0"
    const val timber = "4.7.1"
    const val lifecycle = "2.0.0"
    const val moshi = "1.8.0"
    const val room = "2.2.0-beta01"
    const val paging = "2.1.0"
    const val glide = "4.9.0"
    const val flexBoxLayout = "1.1.0"
}

private object Libraries {
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val koinCore = "org.koin:koin-core:${Versions.koin}"
    const val koinExt = "org.koin:koin-core-ext:${Versions.koin}"
    const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    const val koinScope = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val httpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val moshiConverterAdapter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomExtensions = "androidx.room:room-ktx:${Versions.room}"
    const val roomTesting = "androidx.room:room-testing:${Versions.room}"
    const val paging = "androidx.paging:paging-runtime:${Versions.paging}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val flexBoxLayout = "com.google.android:flexbox:${Versions.flexBoxLayout}"

    const val koinTest = "org.koin:koin-test:${Versions.koin}"
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockito}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}"
    const val junit = "junit:junit:${Versions.junit}"
    const val lifecycleTest = "androidx.arch.core:core-testing:${Versions.lifecycle}"
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}

