package ru.learnsql.mobile.presentation

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.FragmentActivity
import ru.learnsql.app_api.apiFactory
import ru.learnsql.app_api.requireApi
import ru.learnsql.navigation_api.NavigationApi

class SplashActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(mainLooper)
            .postDelayed({
                requireApi(apiFactory[NavigationApi::class]).openMainActivity(this)
                finish()
            }, 1000)
    }
}