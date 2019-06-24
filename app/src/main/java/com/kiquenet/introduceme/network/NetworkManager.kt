package com.kiquenet.introduceme.network

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.kiquenet.introduceme.settings.Settings
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
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

    var defaultRetrofit: Retrofit = buildDefaultRetrofit()

    //should check null because in air plan mode it will be null
    val isOnline: Boolean
        get() {
            val cm = contextApp.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }

    private fun buildDefaultRetrofit(): Retrofit {
        if (defaultRetrofit == null) {
            Log.v(TAG, "Base service URL set to ${settings.baseUrl} and time out set to ${settings.serviceTimeOut}")

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(settings.serviceTimeOut.toLong(), TimeUnit.SECONDS)
                .connectTimeout(settings.connectionTimeOut.toLong(), TimeUnit.SECONDS)
                .addNetworkInterceptor { chain ->

                    var req: Request = chain.request()
                    var resp: Response = Response.Builder()
                        .request(req)
                        .protocol(Protocol.HTTP_1_1)
                        .body(
                            ResponseBody.create(
                                MediaType.parse("application/json"),
                                "{}"
                            )
                        )
                        .message("Problem on service, not FOUND")
                        .code(404)
                        .build()

                    try {
                        Log.v(TAG, "Making " + req.method() + " call to " + req.url())
                        req = req.newBuilder().addHeader("Content-Type", "application/json")
                            .addHeader("Authorization", "introduceMeTokenAuth")
                            .build()

                        resp = chain.proceed(req)
                        Log.v(TAG, "Received " + resp.code() + " response from " + req.method() + " to " + req.url())
                    } catch (e: IOException) {
                        Log.e(
                            TAG,
                            "Problem wen calling interceptor from  ${req.method()} to ${req.url()} ${e.stackTrace}"
                        )
                    }
                    resp
                }
                .build()

            // Create a very simple REST adapter which points to base URL API.
            defaultRetrofit = Retrofit.Builder()
                .baseUrl(settings.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

        return defaultRetrofit
    }
}
