package ru.learnsql.settings.presentation.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import ru.learnsql.app_api.back
import ru.learnsql.app_api.theme.BlueGradient
import ru.learnsql.app_api.theme.LearnSqlTheme
import ru.learnsql.app_api.theme.LightBlue
import ru.learnsql.compose.R.drawable
import ru.learnsql.compose.TopBar
import ru.learnsql.settings.R
import ru.learnsql.settings.R.string

class FAQFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = ComposeView(requireContext()).apply {
        setContent {
            LearnSqlTheme {
                Column(
                    modifier = Modifier
                        .background(BlueGradient)
                        .fillMaxSize(),
                ) {
                    TopBar(title = stringResource(id = string.FAQ)) {
                        back()
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(25.dp),
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(start = 25.dp, end = 25.dp, bottom = 25.dp)
                    ) {
                        FAQItem(title = string.FAQ_first_title, text = string.FAQ_first_description)
                        FAQItem(title = string.FAQ_second_title, text = string.FAQ_second_description)
                        FAQItem(title = string.FAQ_third_title, text = string.FAQ_third_description)
                        FAQItem(title = string.FAQ_fourth_title, text = string.FAQ_fourth_description)
                    }
                }
            }
        }
    }

    @Composable
    fun FAQItem(@StringRes title: Int, @StringRes text: Int) {
        val openState = remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(10.dp))
                .clickable { openState.value = !openState.value }
                .padding()
                .padding(horizontal = 22.dp)
                .defaultMinSize(minHeight = 60.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 18.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_faq), contentDescription = "", tint = LightBlue)
                Text(
                    text = stringResource(id = title),
                    style = LearnSqlTheme.typography.h4,
                    modifier = Modifier
                        .padding(start = 14.dp)
                        .weight(1f)
                )
                Icon(
                    painter = painterResource(id = if (openState.value) R.drawable.ic_crop_down_arrow else drawable.ic_crop_right_arrow),
                    contentDescription = "",
                    tint = if (openState.value) LightBlue else Color.Black,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            if (openState.value) {
                Text(
                    text = stringResource(id = text),
                    style = LearnSqlTheme.typography.body1,
                    modifier = Modifier
                        .padding(bottom = 18.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}