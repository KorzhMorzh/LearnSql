package ru.learnsql.authorization.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.learnsql.authorization.domain.AuthorizationUseCase
import timber.log.Timber
import javax.inject.Inject

internal class AuthorizationViewModel @Inject constructor(
    private val authorizationUseCase: AuthorizationUseCase
) : ViewModel() {
    val username = mutableStateOf("")
    val password = mutableStateOf("")

    fun onUsernameChange(username: String) {
        this.username.value = username
    }

    fun onPasswordChange(password: String) {
        this.password.value = password
    }

    fun login() {
        viewModelScope.launch {
            try {
                authorizationUseCase.login(username.value, password.value)
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        }
    }
}