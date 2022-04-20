package ru.learnsql.app_api

import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface RetrofitFactory {
    fun okHttpBuilder(): OkHttpClient.Builder
    fun retrofitBuilder(): Retrofit.Builder
    fun getDefaultRetrofit(): Retrofit.Builder
}