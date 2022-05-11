package ru.learnsql.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
import ru.learnsql.app_api.theme.LightBlue
import ru.learnsql.app_api.theme.NonActiveGray
import ru.learnsql.app_api.theme.Red
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.compose.AccentButton
import ru.learnsql.compose.TopBar
import ru.learnsql.compose.Wrapper
import ru.learnsql.profile.R
import ru.learnsql.profile.R.string
import ru.learnsql.profile.data.dto.StudentGroups
import ru.learnsql.profile.di.DaggerProfileComponent
import ru.learnsql.profile.presentation.ProfileNavigationEvent.OpenEditPasswordFragment
import javax.inject.Inject

@ExperimentalMaterialApi
class ProfileFragment : Fragment() {
    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ProfileViewModel> { modelFactory }

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
                when (state.value.navigationEvent.take()) {
                    OpenEditPasswordFragment -> {
                        findNavController().navigate(R.id.toEditPasswordFragment)
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
                            modifier = Modifier.background(BlueGradient)
                        ) {
                            TopBar(title = stringResource(id = string.profile)) {
                                back()
                            }
                            Column(
                                modifier = Modifier
                                    .verticalScroll(rememberScrollState())
                                    .padding(start = 40.dp, end = 40.dp, bottom = 40.dp)
                                    .background(Color.White, RoundedCornerShape(10.dp))
                                    .padding(start = 22.dp, end = 22.dp, top = 11.dp, bottom = 22.dp),
                            ) {
                                InputField(field = screenState.username, fieldTitle = string.username, enabled = false)
                                InputField(field = screenState.email, fieldTitle = string.email, onChanged = viewModel::onEmailChanged)
                                InputField(field = screenState.firstName, fieldTitle = string.first_name, onChanged = viewModel::onFirstNameChanged)
                                InputField(field = screenState.lastName, fieldTitle = string.last_name, onChanged = viewModel::onLastNameChanged)
                                Text(
                                    text = stringResource(id = string.group_number),
                                    style = LearnSqlTheme.typography.body2,
                                    color = Gray,
                                    modifier = Modifier.padding(top = 11.dp)
                                )
                                DropdownList(screenState.studentGroups, screenState.groupNumber)
                                if (screenState.fail || screenState.success) {
                                    Text(
                                        text = stringResource(id = if (screenState.fail) string.edit_profile_error else string.edit_profile_success),
                                        color = if (screenState.fail) Red else Green,
                                        modifier = Modifier.padding(bottom = 11.dp),
                                        style = LearnSqlTheme.typography.body2
                                    )
                                }
                                AccentButton(
                                    text = string.change_button,
                                    modifier = Modifier.fillMaxWidth(),
                                    isEnabled = screenState.isButtonEnabled
                                ) {
                                    viewModel.updateUserInfo()
                                }
                                TextButton(onClick = viewModel::openEditPassword) {
                                    Text(
                                        text = stringResource(id = string.change_password),
                                        style = LearnSqlTheme.typography.body2,
                                        color = LightBlue
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun DropdownList(groups: List<StudentGroups>, groupNumber: Int) {
        var expanded by remember { mutableStateOf(false) }
        val group = groups.find { it.id == groupNumber } ?: return
        var selectedGroupTitle by remember { mutableStateOf(group.title) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            BasicTextField(
                value = selectedGroupTitle,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 35.dp)
                    .padding(vertical = 11.dp),
                readOnly = true,
                singleLine = true,
                textStyle = LearnSqlTheme.typography.body1,
            ) { innerTextField ->
                innerTextField()
            }
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                groups.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            viewModel.onGroupNumberChanged(selectionOption.id)
                            selectedGroupTitle = selectionOption.title
                            expanded = false
                        }
                    ) {
                        Text(text = selectionOption.title)
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    private fun PreviewInput() {
        Column(
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp, bottom = 40.dp)
                .background(Color.White, RoundedCornerShape(10.dp))
                .padding(22.dp)
        ) {
            InputField(field = "sdgs", fieldTitle = string.email, onChanged = {})
        }
    }
}

@Composable
internal fun InputField(field: String, @StringRes fieldTitle: Int, enabled: Boolean = true, onChanged: (String) -> Unit = {}) {
    Text(text = stringResource(id = fieldTitle), style = LearnSqlTheme.typography.body2, color = Gray, modifier = Modifier.padding(top = 11.dp))
    BasicTextField(
        value = field,
        onValueChange = onChanged,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 35.dp)
            .padding(vertical = 11.dp),
        enabled = enabled,
        singleLine = true,
        textStyle = LearnSqlTheme.typography.body1.copy(color = if (enabled) Color.Black else NonActiveGray),
    ) { innerTextField ->
        innerTextField()
    }
    Box(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(NonActiveGray)
    )
}