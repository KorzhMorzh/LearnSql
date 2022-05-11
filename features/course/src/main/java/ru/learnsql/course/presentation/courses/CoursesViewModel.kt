package ru.learnsql.course.presentation.courses

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.learnsql.core.BaseState
import ru.learnsql.core.BaseViewModel
import ru.learnsql.core.Event
import ru.learnsql.course.domain.EnrollCourseUseCase
import ru.learnsql.course.domain.GetCoursesUseCase
import ru.learnsql.course.domain.model.Course
import ru.learnsql.course.presentation.courses.CoursesNavigationEvent.OpenCourseDetails
import timber.log.Timber
import javax.inject.Inject

internal sealed interface CoursesNavigationEvent {
    data class OpenCourseDetails(
        val courseId: Int,
        val title: String,
        val isCourseEnrolled: Boolean
    ) : CoursesNavigationEvent
}

internal data class CoursesScreenState(
    val myCourses: List<Course> = listOf(),
    val allCourses: List<Course> = listOf(),
    val selectedTab: Int = 0,
    val loading: Boolean = false,
    val fail: Boolean = false
)

internal class CoursesState(
    override val navigationEvent: Event<CoursesNavigationEvent?> = Event(null),
    override val screenState: CoursesScreenState
) : BaseState<CoursesNavigationEvent, CoursesScreenState>(navigationEvent, screenState)

internal class CoursesViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val enrollCourseUseCase: EnrollCourseUseCase
) : BaseViewModel<CoursesNavigationEvent, CoursesScreenState>() {
    override var state: MutableState<BaseState<CoursesNavigationEvent, CoursesScreenState>> =
        mutableStateOf(CoursesState(screenState = CoursesScreenState()))

    init {
        getCourses()
    }

    fun getCourses() {
        viewModelScope.launch {
            try {
                updateScreen { copy(loading = true, fail = false) }
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

    fun openCourseDetails(course: Course) {
        state.value = state.value.copy(
            navigationEvent = Event(
                OpenCourseDetails(
                    courseId = course.id,
                    title = course.title,
                    isCourseEnrolled = course.isMy
                )
            )
        )
    }

    fun enrollCourse(courseId: Int) {
        viewModelScope.launch {
            try {
                updateScreen { copy(loading = true, fail = false) }
                enrollCourseUseCase.enrollCourse(courseId)
                getCourses()
            } catch (e: Exception) {
                Timber.e(e)
                updateScreen { copy(fail = true) }
            }
        }
    }
}