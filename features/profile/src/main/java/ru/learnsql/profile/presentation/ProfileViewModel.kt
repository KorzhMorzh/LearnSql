package ru.learnsql.profile.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.learnsql.app_api.UserInfo
import ru.learnsql.core.BaseState
import ru.learnsql.core.BaseViewModel
import ru.learnsql.core.Event
import ru.learnsql.profile.data.dto.StudentGroups
import ru.learnsql.profile.domain.GetStudentsGroupsUseCase
import ru.learnsql.profile.domain.UpdateUserInfoUseCase
import ru.learnsql.profile.presentation.ProfileNavigationEvent.OpenEditPasswordFragment
import timber.log.Timber
import javax.inject.Inject

internal sealed interface ProfileNavigationEvent {
    object OpenEditPasswordFragment : ProfileNavigationEvent
}

internal data class ProfileScreenState(
    val username: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val groupNumber: Int = 0,
    val studentGroups: List<StudentGroups> = listOf(),
    val loading: Boolean = false,
    val fail: Boolean = false,
    val success: Boolean = false,
    val isButtonEnabled: Boolean = false
)

internal class ProfileState(
    override val navigationEvent: Event<ProfileNavigationEvent?> = Event(null),
    override val screenState: ProfileScreenState
) : BaseState<ProfileNavigationEvent, ProfileScreenState>(navigationEvent, screenState)

internal class ProfileViewModel @Inject constructor(
    private var userInfo: UserInfo,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val getStudentsGroupsUseCase: GetStudentsGroupsUseCase
) : BaseViewModel<ProfileNavigationEvent, ProfileScreenState>() {
    override var state: MutableState<BaseState<ProfileNavigationEvent, ProfileScreenState>> =
        mutableStateOf(ProfileState(screenState = ProfileScreenState()))

    init {
        updateScreen {
            userInfo.let {
                copy(
                    username = it.username,
                    email = it.email,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    groupNumber = it.groupNumber,
                )
            }
        }
        loadStudentGroups()
    }

    private fun loadStudentGroups() {
        viewModelScope.launch {
            try {
                updateScreen { copy(loading = true, fail = false) }
                getStudentsGroupsUseCase.getStudentGroups().let {
                    updateScreen {
                        copy(
                            studentGroups = it.results
                        )
                    }
                }
            } catch (e: Exception) {
                Timber.e(e)
                updateScreen { copy(fail = true) }
            } finally {
                updateScreen { copy(loading = false) }
            }
        }
    }

    fun updateUserInfo() {
        viewModelScope.launch {
            try {
                updateScreen { copy(loading = true, fail = false, success = false) }
                val screenState = state.value.screenState
                userInfo = updateUserInfoUseCase.updateUserInfo(
                    screenState.email,
                    screenState.firstName,
                    screenState.lastName,
                    screenState.groupNumber
                )
                updateScreen { copy(success = true) }
                validateButton()
            } catch (e: Exception) {
                Timber.e(e)
                updateScreen { copy(fail = true) }
            } finally {
                updateScreen { copy(loading = false) }
            }
        }
    }

    fun onEmailChanged(email: String) {
        updateScreen { copy(email = email, success = false, fail = false) }
        validateButton()
    }

    fun onFirstNameChanged(firstName: String) {
        updateScreen { copy(firstName = firstName, success = false, fail = false) }
        validateButton()
    }

    fun onLastNameChanged(lastName: String) {
        updateScreen { copy(lastName = lastName, success = false, fail = false) }
        validateButton()
    }

    fun onGroupNumberChanged(groupNumber: Int) {
        updateScreen { copy(groupNumber = groupNumber, success = false, fail = false) }
        validateButton()
    }

    private fun validateButton() {
        val screenState = state.value.screenState
        updateScreen {
            copy(
                isButtonEnabled = isEmailValid() &&
                        screenState.firstName.isNotEmpty() &&
                        screenState.lastName.isNotEmpty() &&
                        !isContentTheSame()
            )
        }
    }

    private fun isEmailValid() =
        android.util.Patterns.EMAIL_ADDRESS.matcher(state.value.screenState.email).matches()

    private fun isContentTheSame(): Boolean {
        val screenState = state.value.screenState
        return screenState.email == userInfo.email &&
                screenState.firstName == userInfo.firstName &&
                screenState.lastName == userInfo.lastName &&
                screenState.groupNumber == userInfo.groupNumber
    }

    fun openEditPassword() {
        state.value = state.value.copy(navigationEvent = Event(OpenEditPasswordFragment))
    }
}
