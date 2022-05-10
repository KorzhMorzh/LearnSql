package ru.learnsql.task.presentation.database

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import ru.learnsql.app_api.back
import ru.learnsql.app_api.theme.LearnSqlTheme
import ru.learnsql.compose.TopBar
import ru.learnsql.compose.ZoomableImage
import ru.learnsql.task.R.string

const val DATABASE_IMAGE_NAV_ARG = "databaseImage"

class DatabaseImageFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        ComposeView(requireContext()).apply {
            setContent {
                LearnSqlTheme {
                    Column(
                        modifier = Modifier
                            .background(Color.Black)
                            .fillMaxSize()
                    ) {
                        TopBar(title = stringResource(id = string.database)) {
                            back()
                        }
                        ZoomableImage(url = requireArguments()[DATABASE_IMAGE_NAV_ARG]!!.toString())
                    }
                }
            }
        }
}