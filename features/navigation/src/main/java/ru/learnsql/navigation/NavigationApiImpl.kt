package ru.learnsql.navigation

import android.content.Context
import android.content.Intent
import androidx.navigation.NavController
import ru.learnsql.navigation_api.NavigationApi
import javax.inject.Inject

class NavigationApiImpl @Inject internal constructor() : NavigationApi {
    override fun openMainActivity(context: Context) {
        context.startActivity(Intent(context, NavigationActivity::class.java))
    }

    override fun openAuthorization(navController: NavController) {
        navController.navigate(R.id.toAuthorizationFragment)
    }

    override fun openMainScreen(navController: NavController) {
        navController.navigate(R.id.toMainScreen)
    }
}