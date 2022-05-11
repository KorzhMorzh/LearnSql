package ru.learnsql.authorization

import androidx.navigation.NavController
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.learnsql.app_api.state.StateProvider
import ru.learnsql.authorization.di.Authenticated
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.navigation_api.NavigationApi
import javax.inject.Inject

class AuthorizationApiImpl @Inject internal constructor(
    @Authenticated private val okHttpClient: OkHttpClient,
    @Authenticated private val retrofit: Retrofit,
    private val stateProvider: StateProvider,
    private val navigationApi: NavigationApi
) : AuthorizationApi {

    override fun getOkHttpClient() = okHttpClient

    override fun getRetrofit() = retrofit

    override fun logout(navController: NavController) {
        stateProvider.clearStateInfo()
        navigationApi.openAuthorization(navController)
    }
}