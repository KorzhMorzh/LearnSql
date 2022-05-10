package ru.learnsql.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.learnsql.app_api.apiFactory
import ru.learnsql.app_api.getAppComponentApi
import ru.learnsql.app_api.requireApi
import ru.learnsql.app_api.state.TokenState
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.navigation.R.id
import ru.learnsql.navigation.di.DaggerNavigationComponent
import ru.learnsql.navigation_api.NavigationApi
import timber.log.Timber
import javax.inject.Inject

class NavigationActivity : AppCompatActivity() {
    @Inject lateinit var tokenState: TokenState
    @Inject lateinit var navigationApi: NavigationApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree());

        setContentView(R.layout.main_activity)
        DaggerNavigationComponent.factory().create(
            getAppComponentApi(),
            requireApi(apiFactory[AuthorizationApi::class])
        ).inject(this)

        val navHostFragment =
            supportFragmentManager.findFragmentById(id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(id.bottom_nav)
        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNav.isVisible = destination.id in listOf(
                ru.learnsql.course.R.id.coursesFragment,
                ru.learnsql.methodic.R.id.methodologyFragment,
                ru.learnsql.settings.R.id.settingsFragment
            )
        }

        if (!tokenState.hasToken()) {
            navigationApi.openAuthorization(navController)
        }
    }
}