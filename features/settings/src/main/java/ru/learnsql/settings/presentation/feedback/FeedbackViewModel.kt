package ru.learnsql.settings.presentation.feedback

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.learnsql.core.BaseState
import ru.learnsql.core.BaseViewModel
import ru.learnsql.core.Event
import ru.learnsql.settings.domain.SendFeedbackUseCase
import ru.learnsql.settings.presentation.feedback.FeedbackNavigationEvent.OpenFAQ
import ru.learnsql.settings.presentation.feedback.FeedbackNavigationEvent.ShowSnackBar
import ru.learnsql.settings.presentation.feedback.SnackBarStatus.ERROR
import ru.learnsql.settings.presentation.feedback.SnackBarStatus.SUCCESS
import timber.log.Timber
import javax.inject.Inject

enum class SnackBarStatus {
    SUCCESS, ERROR
}

internal sealed interface FeedbackNavigationEvent {
    object OpenFAQ : FeedbackNavigationEvent
    data class ShowSnackBar(val status: SnackBarStatus) : FeedbackNavigationEvent
}

internal data class FeedbackScreenState(
    val theme: String = "",
    val body: String = "",
    val loading: Boolean = false,
    val fail: Boolean = false,
    val isButtonEnabled: Boolean = false
)

internal class FeedbackState(
    override val navigationEvent: Event<FeedbackNavigationEvent?> = Event(null),
    override val screenState: FeedbackScreenState
) : BaseState<FeedbackNavigationEvent, FeedbackScreenState>(navigationEvent, screenState)

internal class FeedbackViewModel @Inject constructor(
    private val sendFeedbackUseCase: SendFeedbackUseCase
) : BaseViewModel<FeedbackNavigationEvent, FeedbackScreenState>() {
    override var state: MutableState<BaseState<FeedbackNavigationEvent, FeedbackScreenState>> =
        mutableStateOf(FeedbackState(screenState = FeedbackScreenState()))

    fun openFAQ() {
        state.value = state.value.copy(navigationEvent = Event(OpenFAQ))
    }

    private fun showSnackBar(status: SnackBarStatus) {
        state.value = state.value.copy(navigationEvent = Event(ShowSnackBar(status)))
    }

    fun sendFeedback() {
        viewModelScope.launch {
            try {
                updateScreen { copy(loading = true) }
                sendFeedbackUseCase.sendFeedback(
                    state.value.screenState.theme,
                    state.value.screenState.body
                )
                showSnackBar(SUCCESS)
            } catch (ex: Exception) {
                Timber.e(ex)
                showSnackBar(ERROR)
            } finally {
                updateScreen { copy(loading = false) }
            }
        }
    }

    fun onThemeChanged(theme: String) {
        updateScreen { copy(theme = theme) }
        validateButton()
    }

    fun onBodyChanged(body: String) {
        updateScreen { copy(body = body) }
        validateButton()
    }

    private fun validateButton() {
        updateScreen {
            copy(
                isButtonEnabled = state.value.screenState.theme.isNotEmpty() && state.value.screenState.body.isNotEmpty()
            )
        }
    }
}