package ru.learnsql.settings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.learnsql.app_api.apiFactory
import ru.learnsql.app_api.getAppComponentApi
import ru.learnsql.app_api.requireApi
import ru.learnsql.app_api.theme.BlueGradient
import ru.learnsql.app_api.theme.LearnSqlTheme
import ru.learnsql.app_api.theme.LightBlue
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.compose.Wrapper
import ru.learnsql.navigation_api.NavigationApi
import ru.learnsql.settings.R
import ru.learnsql.settings.di.DaggerSettingsComponent
import ru.learnsql.settings.presentation.SettingsNavigationEvent.OpenAuthorization
import ru.learnsql.settings.presentation.SettingsNavigationEvent.OpenFAQ
import ru.learnsql.settings.presentation.SettingsNavigationEvent.OpenFeedback
import ru.learnsql.settings.presentation.SettingsNavigationEvent.OpenProfile
import javax.inject.Inject

class SettingsFragment : Fragment() {
    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    @Inject
    internal lateinit var navigationApi: NavigationApi

    @Inject
    internal lateinit var authorizationApi: AuthorizationApi

    private val viewModel by viewModels<SettingsViewModel> { modelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerSettingsComponent.factory().create(
            getAppComponentApi(),
            requireApi(apiFactory[AuthorizationApi::class]),
            requireApi(apiFactory[NavigationApi::class])
        ).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = ComposeView(requireContext()).apply {
        setContent {
            LearnSqlTheme {
                val state = viewModel.state
                when (state.value.navigationEvent.take()) {
                    OpenFAQ -> {
                        findNavController().navigate(R.id.toFAQFragment)
                    }
                    OpenFeedback -> {
                        findNavController().navigate(R.id.toFeedbackFragment)
                    }
                    OpenProfile -> {
                        navigationApi.openProfileModule(findNavController())
                    }
                    OpenAuthorization -> {
                        authorizationApi.logout(findNavController())
                    }
                    null -> {}
                }
                val screenState = remember(key1 = state.value.screenState) {
                    state.value.screenState
                }
                Wrapper(showLoader = screenState.loading) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .background(BlueGradient)
                                .padding(25.dp),
                            verticalArrangement = Arrangement.spacedBy(25.dp)
                        ) {
                            SettingsItem(icon = R.drawable.ic_edit, title = R.string.profile) {
                                viewModel.openProfile()
                            }

                            SettingsItem(icon = R.drawable.ic_messages, title = R.string.FAQ) {
                                viewModel.openFAQ()
                            }

                            SettingsItem(icon = R.drawable.ic_headphones, title = R.string.feedback) {
                                viewModel.openFeedback()
                            }

                            SettingsItem(icon = R.drawable.ic_right_arrow, title = R.string.logout, false) {
                                viewModel.logout()
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SettingsItem(@DrawableRes icon: Int, @StringRes title: Int, showArrow: Boolean = true, onCLick: () -> Unit) {
        Row(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(10.dp))
                .padding()
                .clickable(onClick = onCLick)
                .padding(horizontal = 22.dp)
                .defaultMinSize(minHeight = 60.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = icon), contentDescription = "", tint = LightBlue)
            Text(
                text = stringResource(id = title),
                style = LearnSqlTheme.typography.h4,
                modifier = Modifier
                    .padding(start = 14.dp)
                    .weight(1f)
            )
            if (showArrow) {
                Icon(painter = painterResource(id = ru.learnsql.compose.R.drawable.ic_crop_right_arrow), contentDescription = "", tint = Color.Black)
            }
        }
    }
}