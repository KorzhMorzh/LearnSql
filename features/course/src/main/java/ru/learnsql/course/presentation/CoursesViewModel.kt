package ru.learnsql.course.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.learnsql.core.BaseState
import ru.learnsql.core.BaseViewModel
import ru.learnsql.course.domain.Course
import ru.learnsql.course.domain.GetCoursesUseCase
import timber.log.Timber
import javax.inject.Inject

internal sealed interface CoursesNavigationEvent {
    object OpenCourses : CoursesNavigationEvent
}

internal data class CoursesScreenState(
    val myCourses: List<Course> = listOf(),
    val allCourses: List<Course> = listOf(),
    val selectedTab: Int = 0,
    val loading: Boolean = false,
    val fail: Boolean = false
)

internal class CoursesState(
    override val navigationEvent: CoursesNavigationEvent? = null,
    override val screenState: CoursesScreenState
) : BaseState<CoursesNavigationEvent, CoursesScreenState>(navigationEvent, screenState)

internal class CoursesViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase
) : BaseViewModel<CoursesNavigationEvent, CoursesScreenState>() {
    override var state: MutableState<BaseState<CoursesNavigationEvent, CoursesScreenState>> =
        mutableStateOf(CoursesState(screenState = CoursesScreenState()))

    init {
        getCourses()
    }

    fun getCourses() {
        viewModelScope.launch {
            try {
                updateScreen { copy(loading = true) }
                getCoursesUseCase.getCourses().let { (allCourses, myCourses) ->
                    updateScreen { copy(loading = false, myCourses = myCourses, allCourses = allCourses) }
                }
            } catch (e: Exception) {
                Timber.e(e)
                updateScreen { copy(loading = false, fail = true) }
            }
        }
    }

    fun selectTab(tabIndex: Int) {
        updateScreen { copy(selectedTab = tabIndex) }
    }
}