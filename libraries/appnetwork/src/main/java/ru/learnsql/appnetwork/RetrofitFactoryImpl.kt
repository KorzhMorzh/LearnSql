package ru.learnsql.appnetwork

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.learnsql.app_api.BuildInfo
import ru.learnsql.app_api.RetrofitFactory
import java.util.concurrent.TimeUnit

class RetrofitFactoryImpl(
    private val context: Context,
    private val buildInfo: BuildInfo
) : RetrofitFactory {

    override fun okHttpBuilder(): OkHttpClient.Builder {
        val timeout = 60L
        val builder = OkHttpClient.Builder()

        builder.connectTimeout(timeout, TimeUnit.SECONDS)
        builder.writeTimeout(timeout, TimeUnit.SECONDS)
        builder.readTimeout(timeout, TimeUnit.SECONDS)

        builder.addNetworkInterceptor(HttpLogging.getInterceptor(buildInfo.isDebug))

        return builder
    }

    override fun retrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(buildInfo.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder().build())
    }

    override fun getDefaultRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
    }
}