package com.github.pedrodimoura.news

import com.github.pedrodimoura.news.Dependencies.appCompat
import com.github.pedrodimoura.news.Dependencies.constraintLayout
import com.github.pedrodimoura.news.Dependencies.coreKtx
import com.github.pedrodimoura.news.Dependencies.coroutinesAndroid
import com.github.pedrodimoura.news.Dependencies.coroutinesCore
import com.github.pedrodimoura.news.Dependencies.espressoCore
import com.github.pedrodimoura.news.Dependencies.gsonConverterAdapter
import com.github.pedrodimoura.news.Dependencies.junit
import com.github.pedrodimoura.news.Dependencies.koinAndroid
import com.github.pedrodimoura.news.Dependencies.koinCore
import com.github.pedrodimoura.news.Dependencies.koinExt
import com.github.pedrodimoura.news.Dependencies.koinScope
import com.github.pedrodimoura.news.Dependencies.koinTest
import com.github.pedrodimoura.news.Dependencies.koinViewModel
import com.github.pedrodimoura.news.Dependencies.kotlinStdLib
import com.github.pedrodimoura.news.Dependencies.lifecycleCompiler
import com.github.pedrodimoura.news.Dependencies.lifecycleExtensions
import com.github.pedrodimoura.news.Dependencies.lifecycleTest
import com.github.pedrodimoura.news.Dependencies.material
import com.github.pedrodimoura.news.Dependencies.mockWebServer
import com.github.pedrodimoura.news.Dependencies.mockitoCore
import com.github.pedrodimoura.news.Dependencies.mockitoInline
import com.github.pedrodimoura.news.Dependencies.okHttp
import com.github.pedrodimoura.news.Dependencies.paging
import com.github.pedrodimoura.news.Dependencies.picasso
import com.github.pedrodimoura.news.Dependencies.retrofit
import com.github.pedrodimoura.news.Dependencies.runner
import com.github.pedrodimoura.news.Dependencies.timber

object Libraries {

    @JvmStatic
    val app = arrayListOf(
        kotlinStdLib,
        appCompat,
        coreKtx,
        constraintLayout,
        material,
        coroutinesCore,
        coroutinesAndroid,
        koinCore,
        koinExt,
        koinAndroid,
        koinScope,
        koinViewModel,
        picasso,
        retrofit,
        okHttp,
        gsonConverterAdapter,
        timber,
        lifecycleExtensions,
        paging
    )

    @JvmStatic
    val appKapt = arrayListOf(
        lifecycleCompiler
    )

    @JvmStatic
    val test = arrayListOf(
        koinTest,
        mockitoCore,
        mockitoInline,
        mockWebServer,
        junit,
        lifecycleTest
    )

    @JvmStatic
    val instrumented = arrayListOf(
        runner,
        espressoCore
    )

}