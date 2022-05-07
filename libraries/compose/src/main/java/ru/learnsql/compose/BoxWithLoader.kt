package ru.learnsql.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.learnsql.app_api.theme.LearnSqlTheme

@Composable
fun Wrapper(
    modifier: Modifier = Modifier,
    showLoader: Boolean,
    showError: Boolean = false,
    retryAction: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(
        modifier.fillMaxSize()
    ) {
        when {
            showLoader -> {
                content()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(LearnSqlTheme.colors.primary.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = LearnSqlTheme.colors.secondary)
                }
            }
            showError -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(horizontal = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // todo icon and text
                    AccentButton(
                        text = R.string.button_retry,
                        modifier = Modifier.wrapContentWidth(),
                        retryAction
                    )
                }
            }
            else -> {
                content()
            }
        }
    }
}