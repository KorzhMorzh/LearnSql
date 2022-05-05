package ru.learnsql.course.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.Companion
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import ru.learnsql.app_api.theme.BlueGradient
import ru.learnsql.app_api.theme.LearnSqlTheme

class CoursesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = ComposeView(requireContext()).apply {
        setContent {
            LearnSqlTheme() {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(modifier = Modifier.background(BlueGradient)) {

                    }

                }
            }
        }
    }
}