package com.github.pedrodimoura.news.common.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.room.Room
import com.github.pedrodimoura.news.common.data.converter.NullToEmptyStringAdapter
import com.github.pedrodimoura.news.common.data.datasource.local.NewsRoomDatabase
import com.github.pedrodimoura.news.common.presentation.lifecycle.NetworkCallback
import com.github.pedrodimoura.news.common.presentation.lifecycle.NetworkLifecycleObserver
import com.github.pedrodimoura.news.common.presentation.viewmodel.ThreadContextProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.*

const val PROPERTY_KEY_BASE_URL = "base_url_pk"
const val BASE_URL = "https://newsapi.org/v2/"
const val API_KEY_NAME = "apiKey"
const val API_KEY_VALUE = "b89305d369ed4dafa1a20ca3de0afee0"
const val CACHE_MAX_SIZE = 10L * 1024 * 1024

val asyncModule = module {
    factory { ThreadContextProvider() }
}

val localModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(), NewsRoomDatabase::class.java, NewsRoomDatabase.NAME).build()
    }
}

val networkModule = module {
    single {
        Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .add(KotlinJsonAdapterFactory())
            .add(NullToEmptyStringAdapter)
            .build()
    }
    single { MoshiConverterFactory.create(get()) }
    single {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) = Timber.d(message)
            })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpLoggingInterceptor
    }
    single {
        OkHttpClient.Builder()
            .addNetworkInterceptor(get<HttpLoggingInterceptor>())
            .addNetworkInterceptor { chain ->
                val request = chain.request()
                val url = request.url

                val newUrl = url.newBuilder()
                    .addQueryParameter(API_KEY_NAME, API_KEY_VALUE).
                        build()

                val newRequest = request.newBuilder()
                    .url(newUrl)
                    .build()

                chain.proceed(newRequest)
            }
            .cache(Cache(androidApplication().cacheDir, CACHE_MAX_SIZE))
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(getProperty(PROPERTY_KEY_BASE_URL, BASE_URL))
            .addConverterFactory(get<MoshiConverterFactory>())
            .client(get())
            .build()
    }

    single {
        androidApplication().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    single { NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build() }

    single {
        NetworkCallback()
    }

    single {
        NetworkLifecycleObserver(
            connectivityManager = get(),
            networkRequest = get(),
            networkCallback = get()
        )
    }

}