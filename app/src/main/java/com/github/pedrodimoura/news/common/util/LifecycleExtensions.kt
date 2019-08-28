package com.github.pedrodimoura.news.common.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <reified T> LifecycleOwner.observe(
    liveData: LiveData<T>,
    crossinline execution: (T) -> Unit
) {
    liveData.observe(this, Observer { execution(it) })
}