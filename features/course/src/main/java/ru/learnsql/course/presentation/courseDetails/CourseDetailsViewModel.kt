package ru.learnsql.course.presentation.courseDetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import ru.learnsql.core.AssistedSavedStateViewModelFactory
import ru.learnsql.core.BaseState
import ru.learnsql.core.BaseViewModel
import ru.learnsql.core.Event
import ru.learnsql.course.domain.GetCourseDetailsUseCase
import ru.learnsql.course.domain.model.Task
import ru.learnsql.course.presentation.courseDetails.CourseDetailsFragment.Companion.COURSE_ID_ARG
import ru.learnsql.course.presentation.courseDetails.CourseDetailsFragment.Companion.COURSE_TITLE_ARG
import ru.learnsql.course.presentation.courseDetails.CourseDetailsNavigationEvent.OpenTaskDetails
import timber.log.Timber

internal sealed interface CourseDetailsNavigationEvent {
    data class OpenTaskDetails(
        val taskId: Int,
        val id: Int,
        val solution: String,
        val taskNumber: Int,
        val isResolved: Boolean,
    ) : CourseDetailsNavigationEvent
}

internal data class CourseDetailsScreenState(
    val title: String = "",
    val tasks: List<Task> = listOf(),
    val tasksCount: Int = 0,
    val tasksResolved: Int = 0,
    val progress: Float = 0f,
    val loading: Boolean = false,
    val fail: Boolean = false
)

internal class CourseDetailsState(
    override val navigationEvent: Event<CourseDetailsNavigationEvent?> = Event(null),
    override val screenState: CourseDetailsScreenState
) : BaseState<CourseDetailsNavigationEvent, CourseDetailsScreenState>(navigationEvent, screenState)

internal class CourseDetailsViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getCourseDetailsUseCase: GetCourseDetailsUseCase,
) : BaseViewModel<CourseDetailsNavigationEvent, CourseDetailsScreenState>() {
    override var state: MutableState<BaseState<CourseDetailsNavigationEvent, CourseDetailsScreenState>> =
        mutableStateOf(CourseDetailsState(screenState = CourseDetailsScreenState()))

    private var courseId: Int = savedStateHandle[COURSE_ID_ARG]!!

    init {
        updateScreen {
            copy(title = savedStateHandle[COURSE_TITLE_ARG]!!)
        }
        getCourseDetails()
    }

    fun getCourseDetails() {
        viewModelScope.launch {
            try {
                updateScreen { copy(loading = true) }
                val courseDetails = getCourseDetailsUseCase.getCourseDetails(courseId)
                updateScreen {
                    copy(
                        tasks = courseDetails.tasks,
                        tasksCount = courseDetails.count,
                        tasksResolved = courseDetails.resolvedCount,
                        progress = courseDetails.resolvedCount.toFloat() / courseDetails.count
                    )
                }
            } catch (e: Exception) {
                Timber.e(e)
                updateScreen { copy(fail = true) }
            } finally {
                updateScreen { copy(loading = false) }
            }
        }
    }

    fun openTask(task: Task, number: Int) {
        state.value = state.value.copy(
            navigationEvent = Event(
                OpenTaskDetails(
                    task.taskId,
                    task.id,
                    task.solution,
                    number,
                    task.isResolved
                )
            )
        )
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory {
        override fun create(savedStateHandle: SavedStateHandle): CourseDetailsViewModel
    }
}