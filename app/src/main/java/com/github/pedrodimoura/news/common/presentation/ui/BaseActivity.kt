package com.github.pedrodimoura.news.common.presentation.ui

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutResId: Int

    protected var isContentAlreadyLoaded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        isContentAlreadyLoaded =
            savedInstanceState?.getBoolean(IS_CONTENT_ALREADY_LOADED_BUNDLE_KEY) ?: false
    }

    protected fun saveContentLoaded(any: ArrayList<out Parcelable>, outState: Bundle) {
        outState.putBoolean(IS_CONTENT_ALREADY_LOADED_BUNDLE_KEY, isContentAlreadyLoaded)
        outState.putParcelableArrayList(CONTENT_BUNDLE_KEY, any)
    }

    companion object {
        @JvmStatic
        val CONTENT_BUNDLE_KEY = "content_bundle_key"
        protected const val IS_CONTENT_ALREADY_LOADED_BUNDLE_KEY = "is_content_loaded_bundle_key"
    }

}