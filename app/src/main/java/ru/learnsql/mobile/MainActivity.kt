package ru.learnsql.mobile

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.learnsql.app_api.getComponentApiFactory
import ru.learnsql.app_api.requireApi
import ru.learnsql.app_api.state.StateProvider
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.mobile.di.DaggerAppComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var stateProvider: StateProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        DaggerAppComponent.factory().create(applicationContext).inject(this)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)
        
        if (!stateProvider.token.hasToken()) {
            navController.navigate(R.id.toAuthorizationFragment)
        }
    }
}