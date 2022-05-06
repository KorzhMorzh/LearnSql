package ru.learnsql.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.learnsql.app_api.theme.LearnSqlTheme
import ru.learnsql.app_api.theme.Orange

@Composable
fun AccentButton(@StringRes text: Int, modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = 50.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Orange)
    ) {
        Text(
            text = stringResource(id = text),
            color = Color.White,
            modifier = Modifier.align(CenterVertically),
            style = LearnSqlTheme.typography.button
        )
    }
}