package ru.learnsql.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.learnsql.app_api.theme.Gray
import ru.learnsql.app_api.theme.LearnSqlTheme
import ru.learnsql.app_api.theme.Orange

@Composable
fun AccentButton(
    @StringRes text: Int,
    modifier: Modifier,
    isEnabled: Boolean = true,
    disabledBackgroundColor: Color = Color.White,
    disabledTextColor: Color = Gray,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Orange,
            disabledBackgroundColor = disabledBackgroundColor,
        ),
        border = BorderStroke(1.dp, Orange)
    ) {
        Text(
            text = stringResource(id = text),
            color = if (isEnabled) Color.White else disabledTextColor,
            modifier = Modifier.align(CenterVertically),
            style = LearnSqlTheme.typography.button
        )
    }
}