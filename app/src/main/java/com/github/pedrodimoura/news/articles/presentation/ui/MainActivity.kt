package com.github.pedrodimoura.news.articles.presentation.ui

import android.os.Bundle
import com.github.pedrodimoura.news.R
import com.github.pedrodimoura.news.common.presentation.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val layoutResId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
    }
}
