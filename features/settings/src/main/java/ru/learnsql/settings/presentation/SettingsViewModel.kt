package ru.learnsql.settings.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import ru.learnsql.core.BaseState
import ru.learnsql.core.BaseViewModel
import ru.learnsql.core.Event
import ru.learnsql.settings.presentation.SettingsNavigationEvent.OpenAuthorization
import ru.learnsql.settings.presentation.SettingsNavigationEvent.OpenFAQ
import ru.learnsql.settings.presentation.SettingsNavigationEvent.OpenFeedback
import ru.learnsql.settings.presentation.SettingsNavigationEvent.OpenProfile
import javax.inject.Inject

internal sealed interface SettingsNavigationEvent {
    object OpenProfile : SettingsNavigationEvent
    object OpenFAQ : SettingsNavigationEvent
    object OpenFeedback : SettingsNavigationEvent
    object OpenAuthorization : SettingsNavigationEvent
}

internal data class SettingsScreenState(
    val loading: Boolean = false,
    val fail: Boolean = false
)

internal class SettingsState(
    override val navigationEvent: Event<SettingsNavigationEvent?> = Event(null),
    override val screenState: SettingsScreenState
) : BaseState<SettingsNavigationEvent, SettingsScreenState>(navigationEvent, screenState)

internal class SettingsViewModel @Inject constructor() : BaseViewModel<SettingsNavigationEvent, SettingsScreenState>() {
    override var state: MutableState<BaseState<SettingsNavigationEvent, SettingsScreenState>> =
        mutableStateOf(SettingsState(screenState = SettingsScreenState()))

    fun openProfile() {
        state.value = state.value.copy(navigationEvent = Event(OpenProfile))
    }

    fun openFAQ() {
        state.value = state.value.copy(navigationEvent = Event(OpenFAQ))
    }

    fun openFeedback() {
        state.value = state.value.copy(navigationEvent = Event(OpenFeedback))
    }

    fun logout() {
        state.value = state.value.copy(navigationEvent = Event(OpenAuthorization))
    }
}