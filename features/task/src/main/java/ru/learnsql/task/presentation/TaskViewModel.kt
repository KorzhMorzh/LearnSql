package ru.learnsql.task.presentation

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
import ru.learnsql.task.data.dto.TaskStatus
import ru.learnsql.task.data.dto.TaskStatus.OK
import ru.learnsql.task.domain.DoTaskUseCase
import ru.learnsql.task.domain.LoadTaskUseCase
import ru.learnsql.task.domain.model.TaskAnswer
import ru.learnsql.task.presentation.TaskNavigationEvent.OpenDatabaseDescription
import ru.learnsql.task.presentation.TaskNavigationEvent.OpenDatabaseImage
import timber.log.Timber

internal sealed interface TaskNavigationEvent {
    data class OpenDatabaseDescription(val databaseDescription: String) : TaskNavigationEvent
    data class OpenDatabaseImage(val databaseImage: String) : TaskNavigationEvent
}

internal data class TaskScreenState(
    val taskText: String = "",
    val taskNumber: Int = 1,
    val solution: String = "",
    val expectedResult: List<List<Any>>? = null,
    val studentResult: List<List<Any>>? = null,
    val status: TaskStatus? = null,
    val message: String? = null,
    val hasDatabaseDescription: Boolean = false,
    val hasDatabaseImage: Boolean = false,
    val isButtonEnabled: Boolean = false,
    val isResolved: Boolean = false,
    val loading: Boolean = false,
    val fail: Boolean = false
)

internal class TaskState(
    override val navigationEvent: Event<TaskNavigationEvent?> = Event(null),
    override val screenState: TaskScreenState
) : BaseState<TaskNavigationEvent, TaskScreenState>(navigationEvent, screenState)

internal class TaskViewModel @AssistedInject constructor(
    private val doTaskUseCase: DoTaskUseCase,
    private val loadTaskUseCase: LoadTaskUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<TaskNavigationEvent, TaskScreenState>() {
    override var state: MutableState<BaseState<TaskNavigationEvent, TaskScreenState>> =
        mutableStateOf(TaskState(screenState = TaskScreenState()))
    private val taskId: Int = savedStateHandle[TASK_ID_ARG]!!
    private val id: Int = savedStateHandle[ID_ARG]!!

    private var dataBaseDescription: String = ""
    private var databaseImage: String = ""
    private var taskAnswer: TaskAnswer? = null

    init {
        val isResolved: Boolean = savedStateHandle[IS_RESOLVED_ARG]!!
        updateScreen {
            copy(
                solution = savedStateHandle[SOLUTION_ARG]!!,
                taskNumber = savedStateHandle[TASK_NUMBER_ARG]!!,
                isResolved = isResolved,
                status = if (isResolved) OK else null
            )
        }
        loadTask()
    }

    fun loadTask() {
        viewModelScope.launch {
            try {
                updateScreen { copy(loading = true, fail = false) }
                loadTaskUseCase.loadTask(taskId).let {
                    updateScreen {
                        copy(
                            taskText = it.taskText,
                            hasDatabaseDescription = it.dataBaseDescription.isNotEmpty(),
                            hasDatabaseImage = it.databaseImage.isNotEmpty()
                        )
                    }
                    validateSolution(state.value.screenState.solution)
                    dataBaseDescription = it.dataBaseDescription
                    databaseImage = it.databaseImage
                }
            } catch (e: Exception) {
                Timber.e(e)
                updateScreen { copy(fail = true) }
            } finally {
                updateScreen { copy(loading = false) }
            }
        }
    }

    fun doTask() {
        viewModelScope.launch {
            try {
                updateScreen { copy(loading = true, fail = false) }
                val screenState = state.value.screenState
                doTaskUseCase.doTask(screenState.solution, taskId, id).let {
                    taskAnswer = it
                    updateScreen {
                        copy(
                            status = it.status,
                            message = it.message,
                            expectedResult = it.expectedResult,
                            studentResult = it.studentResult
                        )
                    }
                }
                validateSolution(screenState.solution)
            } catch (e: Exception) {
                Timber.e(e)
                updateScreen { copy(fail = true) }
            } finally {
                updateScreen { copy(loading = false) }
            }
        }
    }

    fun openDatabaseDescription() {
        state.value = state.value.copy(
            navigationEvent = Event(
                OpenDatabaseDescription(dataBaseDescription)
            )
        )
    }

    fun openDatabaseImage() {
        state.value = state.value.copy(
            navigationEvent = Event(
                OpenDatabaseImage(databaseImage)
            )
        )
    }

    fun onSolutionChange(solution: String) {
        updateScreen { copy(solution = solution) }
        validateSolution(solution)
    }

    private fun validateSolution(solution: String) {
        updateScreen {
            copy(isButtonEnabled = solution.isNotEmpty())
        }
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory {
        override fun create(savedStateHandle: SavedStateHandle): TaskViewModel
    }
}