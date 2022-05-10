package ru.learnsql.task.domain

import ru.learnsql.task.data.TaskNetworkApi
import ru.learnsql.task.data.dto.Task
import javax.inject.Inject

class LoadTaskUseCase @Inject constructor(
    private val taskNetworkApi: TaskNetworkApi
) {
    suspend fun loadTask(taskId: Int): Task {
        val task = taskNetworkApi.loadTask(taskId)
        // May the Lord forgive me for this (2)
        // because receive url starts with http
        val databaseImage = if (!task.databaseImage.contains("https")) {
            task.databaseImage.replace("http", "https")
        } else {
            task.databaseImage
        }
        return task.copy(databaseImage = databaseImage)
    }
}