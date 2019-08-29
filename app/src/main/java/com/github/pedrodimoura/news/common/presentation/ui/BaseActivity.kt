package com.github.pedrodimoura.news.common.presentation.ui

import android.os.Bundle
import android.view.Menu
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutResId: Int

    @get:MenuRes
    protected abstract val menuRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(menuRes, menu)
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        @JvmStatic
        val CONTENT_BUNDLE_KEY = "content_bundle_key"
        protected const val IS_CONTENT_ALREADY_LOADED_BUNDLE_KEY = "is_content_loaded_bundle_key"
    }

}