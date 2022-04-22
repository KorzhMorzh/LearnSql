package ru.learnsql.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.learnsql.app_api.theme.Gray
import ru.learnsql.app_api.theme.LearnSqlTheme
import ru.learnsql.app_api.theme.LightBlue
import ru.learnsql.app_api.theme.NonActiveGray
import ru.learnsql.app_api.theme.Orange

@Composable
fun InputField(
    value: String,
    @DrawableRes leadingIcon: Int,
    @StringRes placeholder: Int,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White, RoundedCornerShape(6.dp)),
        leadingIcon = {
            Box(
                Modifier
                    .width(45.dp)
                    .height(50.dp)
            ) {
                Image(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = "",
                    modifier = Modifier.align(Center),
                    alignment = Center,
                    colorFilter = ColorFilter.tint(if (value.isNotEmpty()) LightBlue else NonActiveGray)
                )
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(50.dp)
                        .align(CenterEnd)
                        .background(if (value.isNotEmpty()) LightBlue else NonActiveGray)
                )
            }
        },
        placeholder = {
            Text(text = stringResource(id = placeholder), color = Gray, style = LearnSqlTheme.typography.body1)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = LightBlue,
            unfocusedBorderColor = NonActiveGray,
            errorBorderColor = Orange,
            textColor = Color.Black,
        ),
        isError = isError,
        shape = RoundedCornerShape(6.dp),
        singleLine = true,
        textStyle = LearnSqlTheme.typography.body1
    )
}

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