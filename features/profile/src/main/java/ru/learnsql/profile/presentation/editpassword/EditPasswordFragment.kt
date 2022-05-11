package ru.learnsql.profile.presentation.editpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.learnsql.app_api.apiFactory
import ru.learnsql.app_api.back
import ru.learnsql.app_api.getAppComponentApi
import ru.learnsql.app_api.requireApi
import ru.learnsql.app_api.theme.BlueGradient
import ru.learnsql.app_api.theme.Green
import ru.learnsql.app_api.theme.LearnSqlTheme
import ru.learnsql.app_api.theme.Red
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.compose.AccentButton
import ru.learnsql.compose.TopBar
import ru.learnsql.compose.Wrapper
import ru.learnsql.profile.R.string
import ru.learnsql.profile.di.DaggerProfileComponent
import ru.learnsql.profile.presentation.InputField
import javax.inject.Inject

class EditPasswordFragment : Fragment() {
    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<EditPasswordViewModel> { modelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerProfileComponent.factory().create(
            getAppComponentApi(),
            requireApi(apiFactory[AuthorizationApi::class]),
        ).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = ComposeView(requireContext()).apply {
        setContent {
            LearnSqlTheme {
                val state = viewModel.state
                val screenState = remember(key1 = state.value.screenState) {
                    state.value.screenState
                }
                Wrapper(showLoader = screenState.loading) {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier.background(BlueGradient)
                        ) {
                            TopBar(title = stringResource(id = string.change_password)) {
                                back()
                            }
                            Box(
                                modifier = Modifier.fillMaxSize(),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(start = 40.dp, end = 40.dp, bottom = 40.dp)
                                        .background(Color.White, RoundedCornerShape(10.dp))
                                        .padding(start = 22.dp, end = 22.dp, top = 11.dp, bottom = 22.dp)
                                        .align(Alignment.Center),
                                ) {
                                    InputField(
                                        field = screenState.currentPassword,
                                        fieldTitle = string.old_password,
                                        onChanged = viewModel::onCurrentPasswordChange
                                    )
                                    InputField(
                                        field = screenState.newPassword,
                                        fieldTitle = string.new_password,
                                        onChanged = viewModel::onNewPasswordChange
                                    )
                                    InputField(
                                        field = screenState.reNewPassword,
                                        fieldTitle = string.repeat_password,
                                        onChanged = viewModel::onReNewPasswordChange
                                    )
                                    if (screenState.fail || screenState.success) {
                                        Text(
                                            text = stringResource(id = if (screenState.fail) string.edit_password_error else string.edit_password_success),
                                            color = if (screenState.fail) Red else Green,
                                            modifier = Modifier.padding(top = 11.dp),
                                            style = LearnSqlTheme.typography.body2
                                        )
                                    }

                                    AccentButton(
                                        text = string.change_password,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 11.dp),
                                        isEnabled = screenState.isButtonEnabled
                                    ) {
                                        viewModel.editPassword()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}