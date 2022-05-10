package ru.learnsql.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.learnsql.app_api.theme.LearnSqlTheme
import ru.learnsql.compose.R.drawable

@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit
) {
    Row(
        modifier
            .padding(15.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = drawable.ic_left_arrow),
            contentDescription = "",
            modifier = Modifier.clickable(onClick = onNavigationClick),
            tint = Color.White
        )
        Text(
            text = title,
            color = Color.White,
            style = LearnSqlTheme.typography.h2,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

    }
}