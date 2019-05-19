package com.kiquenet.introduceme.ui.network

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kiquenet.introduceme.ui.settings.Settings
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author n.diazgranados
 */
class NetworkManager @Inject constructor(
    private val contextApp: Context,
    private val settings: Settings
) {

    companion object {
        private val TAG = NetworkManager::class.java.name
    }

    var defaultRetrofit: Retrofit? = null
        private set

    //should check null because in air plan mode it will be null
    val isOnline: Boolean
        get() {
            val cm = contextApp.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }

    init {
        createDefaultRetrofit()
    }

    private fun createDefaultRetrofit() {
        if (defaultRetrofit == null) {
            Log.v(TAG, "Base service URL set to ${settings.baseUrl} and time out set to ${settings.serviceTimeOut}")

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(settings.serviceTimeOut.toLong(), TimeUnit.SECONDS)
                .connectTimeout(settings.connectionTimeOut.toLong(), TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    var req = chain.request()
                    Log.v(TAG, "Making " + req.method() + " call to " + req.url())
                    req = req.newBuilder().addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "introduceMeTokenAuth")
                        .build()
                    val resp = chain.proceed(req)
                    Log.v(TAG, "Received " + resp.code() + " response from " + req.method() + " to " + req.url())
                    resp
                }
                .build()

            // Create a very simple REST adapter which points to base URL API.
            defaultRetrofit = Retrofit.Builder()
                .baseUrl(settings.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(okHttpClient)
                .build()
        }
    }
}
