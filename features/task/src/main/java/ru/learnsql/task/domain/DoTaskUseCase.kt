package ru.learnsql.task.domain

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ru.learnsql.app_api.const.TEXT_PLAIN
import ru.learnsql.task.data.TaskNetworkApi
import ru.learnsql.task.domain.model.TaskAnswer
import javax.inject.Inject

class DoTaskUseCase @Inject constructor(
    private val taskNetworkApi: TaskNetworkApi
) {
    suspend fun doTask(solution: String, taskId: Int, status: Boolean, id: Int): TaskAnswer {
        val response = taskNetworkApi.doTask(
            solution.toRequestBody(TEXT_PLAIN.toMediaType()),
            taskId.toString().toRequestBody(TEXT_PLAIN.toMediaType()),
            (if (status) "1" else "0").toRequestBody(TEXT_PLAIN.toMediaType()),
            id.toString().toRequestBody(TEXT_PLAIN.toMediaType()),
        )
        return TaskAnswer.map(response)
    }
}