package com.github.pedrodimoura.news.common.presentation.ui

import android.os.Bundle
import android.view.Menu
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import com.github.pedrodimoura.news.common.presentation.lifecycle.ConnectionStatus
import com.github.pedrodimoura.news.common.presentation.lifecycle.NetworkLifecycleObserver
import com.github.pedrodimoura.news.common.util.observe
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutResId: Int

    @get:MenuRes
    protected abstract val menuRes: Int

    private val networkObserver: NetworkLifecycleObserver by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        lifecycle.addObserver(networkObserver)
    }

    override fun onResume() {
        super.onResume()
        observe(networkObserver.isConnected) {
            when (it) {
                is ConnectionStatus.Connected -> isDeviceConnected()
                is ConnectionStatus.Disconnected -> isDeviceDisconnected()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(networkObserver)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(menuRes, menu)
        return super.onCreateOptionsMenu(menu)
    }

    abstract fun isDeviceConnected()

    abstract fun isDeviceDisconnected()


}