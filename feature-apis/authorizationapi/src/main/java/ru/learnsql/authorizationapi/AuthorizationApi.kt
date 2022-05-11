package ru.learnsql.authorizationapi

import androidx.navigation.NavController
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AuthorizationApi {
    fun getOkHttpClient(): OkHttpClient
    fun getRetrofit(): Retrofit

    fun logout(navController: NavController)
}