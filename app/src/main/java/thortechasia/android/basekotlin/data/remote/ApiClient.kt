package thortechasia.android.basekotlin.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import thortechasia.android.basekotlin.BuildConfig
import thortechasia.android.basekotlin.data.pref.PreferencesHelper
import java.util.concurrent.TimeUnit


const val cacheSize = (5 * 1024 * 1024).toLong()

fun provideOkHttpClient(context: Context, authInterceptor : AuthInterceptor,  cacheInterceptor: CacheInterceptor): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    httpClient.apply {
        cache(Cache(context.cacheDir, cacheSize))
        writeTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        callTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(logging)
            addInterceptor(ChuckerInterceptor(context))
        }
        addInterceptor(authInterceptor)
        addInterceptor(cacheInterceptor)
    }
    return httpClient.build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}


class AuthInterceptor(val prefHelper: PreferencesHelper) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .addHeader("Authorization", "Bearer " + prefHelper.getString(PreferencesHelper.ACCESS_TOKEN))
            .build()
        return chain.proceed(request)
    }
}


class CacheInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = if (hasNetwork(context)!!) {
            request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
        } else {
            request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
        }
        return chain.proceed(request)
    }
}


fun hasNetwork(context: Context): Boolean? {
    var isConnected: Boolean? = false // Initial Value
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}

