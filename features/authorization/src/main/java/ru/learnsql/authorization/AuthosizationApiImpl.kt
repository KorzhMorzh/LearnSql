package ru.learnsql.authorization

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.learnsql.authorization.di.Authenticated
import ru.learnsql.authorizationapi.AuthorizationApi
import javax.inject.Inject

class AuthorizationApiImpl @Inject internal constructor(
    @Authenticated private val okHttpClient: OkHttpClient,
    @Authenticated private val retrofit: Retrofit
) : AuthorizationApi {

    override fun getOkHttpClient() = okHttpClient

    override fun getRetrofit() = retrofit
    override fun startLoginFragment(context: Context) {
        TODO()
    }

    override fun quitToLoginFragment(context: Context) {
        TODO()
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }
}