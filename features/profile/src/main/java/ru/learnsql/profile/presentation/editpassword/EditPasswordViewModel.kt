package ru.learnsql.profile.presentation.editpassword

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.learnsql.core.BaseState
import ru.learnsql.core.BaseViewModel
import ru.learnsql.core.Event
import ru.learnsql.profile.domain.EditPasswordUseCase
import timber.log.Timber
import javax.inject.Inject

internal sealed interface NavigationEvent

internal data class EditPasswordScreenState(
    val currentPassword: String = "",
    val newPassword: String = "",
    val reNewPassword: String = "",
    val loading: Boolean = false,
    val fail: Boolean = false,
    val success: Boolean = false,
    val isButtonEnabled: Boolean = false
)

internal class EditPasswordState(
    override val navigationEvent: Event<NavigationEvent?> = Event(null),
    override val screenState: EditPasswordScreenState
) : BaseState<NavigationEvent, EditPasswordScreenState>(navigationEvent, screenState)

internal class EditPasswordViewModel @Inject constructor(
    private val editPasswordUseCase: EditPasswordUseCase
) : BaseViewModel<NavigationEvent, EditPasswordScreenState>() {
    override var state: MutableState<BaseState<NavigationEvent, EditPasswordScreenState>> =
        mutableStateOf(EditPasswordState(screenState = EditPasswordScreenState()))

    fun onCurrentPasswordChange(currentPassword: String) {
        updateScreen { copy(currentPassword = currentPassword, success = false, fail = false) }
        validateButton()
    }

    fun onNewPasswordChange(newPassword: String) {
        updateScreen { copy(newPassword = newPassword, success = false, fail = false) }
        validateButton()
    }

    fun onReNewPasswordChange(reNewPassword: String) {
        updateScreen { copy(reNewPassword = reNewPassword, success = false, fail = false) }
        validateButton()
    }

    fun editPassword() {
        viewModelScope.launch {
            try {
                updateScreen { copy(loading = true, fail = false, success = false) }
                state.value.screenState.let {
                    editPasswordUseCase.editPassword(it.currentPassword, it.newPassword, it.reNewPassword)
                }
                updateScreen { copy(success = true) }
            } catch (e: Exception) {
                Timber.e(e)
                updateScreen { copy(fail = true) }
            } finally {
                updateScreen { copy(loading = false) }
            }
        }
    }

    private fun validateButton() {
        state.value.screenState.let {
            updateScreen {
                copy(
                    isButtonEnabled = it.currentPassword.isNotEmpty() &&
                            it.newPassword == it.reNewPassword &&
                            it.newPassword.isNotEmpty()
                )
            }
        }
    }
}
