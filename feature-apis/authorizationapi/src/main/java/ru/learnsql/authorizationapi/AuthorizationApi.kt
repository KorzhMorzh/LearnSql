package ru.learnsql.authorizationapi

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AuthorizationApi {
    fun getOkHttpClient(): OkHttpClient
    fun getRetrofit(): Retrofit

    fun startLoginFragment(context: Context)

    suspend fun logout()
}