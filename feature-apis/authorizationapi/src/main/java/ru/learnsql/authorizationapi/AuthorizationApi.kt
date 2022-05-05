package ru.learnsql.authorizationapi

import android.content.Context
import androidx.navigation.NavController
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AuthorizationApi {
    fun getOkHttpClient(): OkHttpClient
    fun getRetrofit(): Retrofit

    suspend fun logout()
}