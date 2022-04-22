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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.learnsql.app_api.getAppComponentApi
import ru.learnsql.app_api.theme.BlueGradient
import ru.learnsql.app_api.theme.LearnSqlTheme
import ru.learnsql.app_api.theme.WhiteGray
import ru.learnsql.authorization.R.string
import ru.learnsql.authorization.di.DaggerAuthorizationComponent
import ru.learnsql.compose.AccentButton
import ru.learnsql.compose.InputField
import ru.learnsql.compose.R.drawable
import javax.inject.Inject

class AuthorizationFragment : Fragment() {
    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AuthorizationViewModel> { modelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAuthorizationComponent.factory().create(getAppComponentApi()).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                LearnSqlTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                    ) {
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
                                value = viewModel.username.value,
                                modifier = Modifier.padding(top = 20.dp),
                                leadingIcon = drawable.ic_letter,
                                placeholder = string.login_placeholder,
                                onValueChange = { viewModel.onUsernameChange(it) })
                            InputField(
                                value = viewModel.password.value,
                                modifier = Modifier.padding(top = 20.dp),
                                leadingIcon = drawable.ic_lock,
                                placeholder = string.password_placeholder,
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
