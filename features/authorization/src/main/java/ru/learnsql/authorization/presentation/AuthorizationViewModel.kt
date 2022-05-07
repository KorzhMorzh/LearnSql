package ru.learnsql.authorization.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.learnsql.authorization.domain.AuthorizationUseCase
import ru.learnsql.authorization.presentation.AuthorizationNavigationEvent.OpenMain
import ru.learnsql.core.BaseState
import ru.learnsql.core.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

internal sealed interface AuthorizationNavigationEvent {
    object OpenMain : AuthorizationNavigationEvent
    object OpenRegistration : AuthorizationNavigationEvent
}

internal data class AuthorizationScreenState(
    val username: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val loginFailed: Boolean = false
)

internal data class AuthorizationState(
    override val navigationEvent: AuthorizationNavigationEvent? = null,
    override val screenState: AuthorizationScreenState
) : BaseState<AuthorizationNavigationEvent, AuthorizationScreenState>(navigationEvent, screenState)

internal class AuthorizationViewModel @Inject constructor(
    private val authorizationUseCase: AuthorizationUseCase
) : BaseViewModel<AuthorizationNavigationEvent, AuthorizationScreenState>() {

    override var state: MutableState<BaseState<AuthorizationNavigationEvent, AuthorizationScreenState>> =
        mutableStateOf(AuthorizationState(screenState = AuthorizationScreenState()))

    fun onUsernameChange(username: String) {
        updateScreen {
            copy(username = username)
        }
    }

    fun onPasswordChange(password: String) {
        updateScreen {
            copy(password = password)
        }
    }

    fun login() {
        viewModelScope.launch {
            try {
                updateScreen {
                    copy(loading = true)
                }
                authorizationUseCase.login(state.value.screenState.username, state.value.screenState.password)
                state.value = state.value.copy(navigationEvent = OpenMain)
            } catch (ex: Exception) {
                Timber.e(ex)
                updateScreen {
                    copy(loginFailed = true)
                }
            } finally {
                updateScreen {
                    copy(loading = false)
                }
            }
        }
    }
}