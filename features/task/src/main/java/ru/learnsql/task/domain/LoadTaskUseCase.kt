package ru.learnsql.task.domain

import ru.learnsql.task.data.TaskNetworkApi
import javax.inject.Inject

class LoadTaskUseCase @Inject constructor(
    private val taskNetworkApi: TaskNetworkApi
) {
    suspend fun loadTask(taskId: Int) = taskNetworkApi.loadTask(taskId)
}