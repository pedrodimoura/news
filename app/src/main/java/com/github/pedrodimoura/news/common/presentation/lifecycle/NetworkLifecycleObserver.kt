package com.github.pedrodimoura.news.common.presentation.lifecycle

import android.net.ConnectivityManager
import android.net.NetworkRequest
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent

class NetworkLifecycleObserver(
    private val connectivityManager: ConnectivityManager,
    private val networkRequest: NetworkRequest,
    private val networkCallback: NetworkCallback
) : LifecycleObserver {

    val isConnected: LiveData<ConnectionStatus> = networkCallback.isConnected()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() = connectivityManager.requestNetwork(networkRequest, networkCallback)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() = try {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    } catch (e: Exception) {}

}