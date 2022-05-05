package ru.learnsql.mobile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.FragmentActivity

class SplashActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(mainLooper)
            .postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 1000)
    }
}