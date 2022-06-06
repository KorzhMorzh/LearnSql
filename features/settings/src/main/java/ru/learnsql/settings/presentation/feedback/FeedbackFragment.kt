package ru.learnsql.settings.presentation.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.learnsql.app_api.apiFactory
import ru.learnsql.app_api.back
import ru.learnsql.app_api.getAppComponentApi
import ru.learnsql.app_api.requireApi
import ru.learnsql.app_api.theme.BlueGradient
import ru.learnsql.app_api.theme.Gray
import ru.learnsql.app_api.theme.Green
import ru.learnsql.app_api.theme.LearnSqlTheme
import ru.learnsql.app_api.theme.Red
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.compose.AccentButton
import ru.learnsql.compose.TopBar
import ru.learnsql.compose.Wrapper
import ru.learnsql.navigation_api.NavigationApi
import ru.learnsql.settings.R
import ru.learnsql.settings.R.string
import ru.learnsql.settings.di.DaggerSettingsComponent
import ru.learnsql.settings.presentation.feedback.FeedbackNavigationEvent.OpenFAQ
import ru.learnsql.settings.presentation.feedback.FeedbackNavigationEvent.ShowSnackBar
import javax.inject.Inject

class FeedbackFragment : Fragment() {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    @Inject
    internal lateinit var navigationApi: NavigationApi

    private val viewModel by viewModels<FeedbackViewModel> { modelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerSettingsComponent.factory().create(
            getAppComponentApi(),
            requireApi(apiFactory[AuthorizationApi::class]),
            requireApi(apiFactory[NavigationApi::class])
        ).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        ComposeView(requireContext()).apply {
            setContent {
                LearnSqlTheme {
                    val state = viewModel.state

                    when (state.value.navigationEvent.take()) {
                        OpenFAQ -> {
                            findNavController().navigate(R.id.toFAQFragment)
                        }
                        is ShowSnackBar -> {}
                        null -> {}
                    }
                    val screenState = remember(key1 = state.value.screenState) {
                        state.value.screenState
                    }
                    Wrapper(showLoader = screenState.loading) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Column(
                                modifier = Modifier.background(BlueGradient),
                            ) {
                                TopBar(title = stringResource(id = string.feedback)) {
                                    back()
                                }
                                Column(
                                    modifier = Modifier
                                        .verticalScroll(rememberScrollState())
                                        .padding(start = 25.dp, end = 25.dp, bottom = 25.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = string.feedback_description),
                                        color = Color.White,
                                        style = LearnSqlTheme.typography.body1,
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .padding(start = 25.dp, end = 25.dp),
                                        textAlign = TextAlign.Center
                                    )

                                    OutlinedTextField(
                                        value = screenState.theme,
                                        onValueChange = viewModel::onThemeChanged, modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 15.dp)
                                            .background(Color.White, RoundedCornerShape(6.dp))
                                            .defaultMinSize(minHeight = 40.dp),
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            textColor = Color.Black,
                                        ),
                                        shape = RoundedCornerShape(6.dp),
                                        singleLine = true,
                                        textStyle = LearnSqlTheme.typography.h4,
                                        placeholder = {
                                            Text(text = stringResource(id = string.feedback_theme), style = LearnSqlTheme.typography.h4, color = Gray)
                                        }
                                    )

                                    OutlinedTextField(
                                        value = screenState.body,
                                        onValueChange = viewModel::onBodyChanged, modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 12.dp)
                                            .background(Color.White, RoundedCornerShape(6.dp))
                                            .defaultMinSize(minHeight = 200.dp),
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            textColor = Color.Black,
                                        ),
                                        shape = RoundedCornerShape(6.dp),
                                        textStyle = LearnSqlTheme.typography.body1,
                                        placeholder = {
                                            Text(
                                                text = stringResource(id = string.feedback_body),
                                                style = LearnSqlTheme.typography.body1,
                                                color = Gray
                                            )
                                        }
                                    )

                                    if (screenState.fail || screenState.success) {
                                        Text(
                                            text = stringResource(id = if (screenState.fail) string.feedback_sent_error else string.feedback_sent_success),
                                            color = if (screenState.fail) Red else Green,
                                            modifier = Modifier.padding(top = 11.dp),
                                            style = LearnSqlTheme.typography.body2
                                        )
                                    }

                                    AccentButton(
                                        text = string.feedback_send,
                                        isEnabled = screenState.isButtonEnabled,
                                        modifier = Modifier
                                            .align(Alignment.End)
                                            .padding(top = 27.dp)
                                            .defaultMinSize(minHeight = 42.dp)
                                            .fillMaxWidth(),
                                        disabledBackgroundColor = Color(android.R.color.transparent),
                                        disabledTextColor = Color.White
                                    ) {
                                        viewModel.sendFeedback()
                                    }

                                    Text(
                                        text = stringResource(id = string.FAQ),
                                        color = Color.White,
                                        style = LearnSqlTheme.typography.h4,
                                        modifier = Modifier
                                            .align(Alignment.End)
                                            .padding(top = 12.dp)
                                            .clickable { viewModel.openFAQ() }
                                    )

                                }
                            }
                        }
                    }
                }
            }
        }
}