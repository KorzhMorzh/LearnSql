package ru.learnsql.authorization.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ru.learnsql.app_api.apiFactory
import ru.learnsql.app_api.getAppComponentApi
import ru.learnsql.app_api.requireApi
import ru.learnsql.app_api.theme.BlueGradient
import ru.learnsql.app_api.theme.LearnSqlTheme
import ru.learnsql.app_api.theme.WhiteGray
import ru.learnsql.authorization.R.string
import ru.learnsql.authorization.di.DaggerAuthorizationComponent
import ru.learnsql.authorization.presentation.AuthorizationNavigationEvent.OpenMain
import ru.learnsql.authorization.presentation.AuthorizationNavigationEvent.OpenRegistration
import ru.learnsql.compose.AccentButton
import ru.learnsql.compose.InputField
import ru.learnsql.compose.R.drawable
import ru.learnsql.compose.Wrapper
import ru.learnsql.navigation_api.NavigationApi
import javax.inject.Inject

class AuthorizationFragment : Fragment() {
    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    @Inject
    internal lateinit var navigationApi: NavigationApi

    private val viewModel by viewModels<AuthorizationViewModel> { modelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAuthorizationComponent.factory().create(
            getAppComponentApi(),
            requireApi(apiFactory[NavigationApi::class])
        ).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                LearnSqlTheme {
                    val state = viewModel.state
                    when (state.value.navigationEvent) {
                        OpenMain -> navigationApi.openMainScreen(findNavController())
                        OpenRegistration -> TODO()
                    }
                    val screenState = remember(key1 = state.value.screenState) {
                        state.value.screenState
                    }
                    Wrapper(showLoader = screenState.loading) {
                        Surface(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            var passwordVisible: Boolean by rememberSaveable { mutableStateOf(false) }
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = CenterHorizontally,
                                modifier = Modifier
                                    .background(brush = BlueGradient)
                                    .padding(start = 25.dp, end = 25.dp, bottom = 40.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = drawable.ic_learnsql),
                                    contentDescription = "",
                                    modifier = Modifier.align(CenterHorizontally)
                                )
                                InputField(
                                    value = screenState.username,
                                    modifier = Modifier.padding(top = 20.dp),
                                    leadingIcon = drawable.ic_letter,
                                    placeholder = string.login_placeholder,
                                    onValueChange = { viewModel.onUsernameChange(it) })
                                InputField(
                                    value = screenState.password,
                                    modifier = Modifier.padding(top = 20.dp),
                                    leadingIcon = drawable.ic_lock,
                                    placeholder = string.password_placeholder,
                                    trailingIcon = {
                                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                            Image(
                                                painter = painterResource(id = if (passwordVisible) drawable.ic_password_on else drawable.ic_password_off),
                                                contentDescription = ""
                                            )
                                        }
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                    onValueChange = { viewModel.onPasswordChange(it) }
                                )
                                AccentButton(
                                    modifier = Modifier
                                        .padding(top = 20.dp)
                                        .fillMaxWidth(),
                                    text = string.login,
                                    onClick = { viewModel.login() }
                                )
                                Row(
                                    horizontalArrangement = SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 10.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = string.signup),
                                        color = WhiteGray,
                                        style = LearnSqlTheme.typography.body2
                                    )
                                    Text(
                                        text = stringResource(id = string.forget_password),
                                        color = WhiteGray,
                                        style = LearnSqlTheme.typography.body2
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
