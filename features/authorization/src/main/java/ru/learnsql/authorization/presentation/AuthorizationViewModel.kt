package ru.learnsql.authorization.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.learnsql.authorization.domain.AuthorizationUseCase
import ru.learnsql.authorization.presentation.AuthorizationNavigationEvent.OpenMain
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
    val navigationEvent: AuthorizationNavigationEvent? = null,
    val screenState: AuthorizationScreenState
)

internal class AuthorizationViewModel @Inject constructor(
    private val authorizationUseCase: AuthorizationUseCase
) : ViewModel() {

    var state by mutableStateOf(AuthorizationState(screenState = AuthorizationScreenState()))
        private set

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
                authorizationUseCase.login(state.screenState.username, state.screenState.password)
                state = state.copy(navigationEvent = OpenMain)
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

    private fun updateScreen(block: AuthorizationScreenState.() -> AuthorizationScreenState) {
        state = state.copy(
            screenState = block(state.screenState)
        )
    }
}