package com.github.pedrodimoura.news.common.presentation.lifecycle

import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NetworkCallback : ConnectivityManager.NetworkCallback() {

    private val isConnected = MutableLiveData<ConnectionStatus>()

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        isConnected.postValue(ConnectionStatus.Connected)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        isConnected.postValue(ConnectionStatus.Disconnected)
    }

    fun isConnected(): LiveData<ConnectionStatus> = isConnected

}