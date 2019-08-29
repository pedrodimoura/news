package com.github.pedrodimoura.news.common.presentation.ui

import android.os.Bundle
import android.view.Menu
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import com.github.pedrodimoura.news.common.presentation.lifecycle.ConnectionStatus
import com.github.pedrodimoura.news.common.presentation.lifecycle.NetworkLifecycleObserver
import com.github.pedrodimoura.news.common.util.observe
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.scope.currentScope

abstract class BaseActivity : AppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutResId: Int

    @get:MenuRes
    protected abstract val menuRes: Int

    private val networkObserver: NetworkLifecycleObserver by currentScope.inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        lifecycle.addObserver(networkObserver)
    }

    override fun onResume() {
        super.onResume()
        observe(networkObserver.isConnected) {
            when (it) {
                is ConnectionStatus.Connected -> notifyIsConnected()
                is ConnectionStatus.Disconnected -> notifyIsDisconnected()
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

    private fun notifyIsConnected() = showSnackbar("Connected", Snackbar.LENGTH_SHORT)

    private fun notifyIsDisconnected() = showSnackbar("Disconnected", Snackbar.LENGTH_SHORT)

    private fun showSnackbar(message: String, duration: Int) {
        Snackbar.make(window.decorView.rootView, message, duration).show()
    }

}