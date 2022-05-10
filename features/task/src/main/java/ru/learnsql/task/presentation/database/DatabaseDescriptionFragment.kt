package ru.learnsql.task.presentation.database

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import ru.learnsql.app_api.back
import ru.learnsql.app_api.theme.BlueGradient
import ru.learnsql.app_api.theme.LearnSqlTheme
import ru.learnsql.compose.TopBar
import ru.learnsql.task.R

const val DATABASE_DESCRIPTION_NAV_ARG = "databaseDescription"

class DatabaseDescriptionFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        ComposeView(requireContext()).apply {
            setContent {
                LearnSqlTheme {
                    Column(modifier = Modifier
                        .background(BlueGradient)
                        .fillMaxSize()) {
                        TopBar(
                            title = stringResource(R.string.database),
                            modifier = Modifier.background(Color.Transparent)
                        ) { back() }
                        Column(
                            modifier = Modifier
                                .background(Color.White, RoundedCornerShape(15.dp))
                                .padding(horizontal = 25.dp)
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            val description: String = requireArguments()[DATABASE_DESCRIPTION_NAV_ARG].toString()
                            Html(text = description)
                        }
                    }
                }
            }
        }

    @Composable
    fun Html(text: String) {
        AndroidView(factory = { context ->
            TextView(context).apply {
                setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY))
                setTextColor(resources.getColor(android.R.color.black))
            }
        }, modifier = Modifier.padding(top = 12.dp))
    }
}