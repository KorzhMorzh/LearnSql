package ru.learnsql.task.domain.model

import ru.learnsql.task.data.dto.TaskAnswerDto
import ru.learnsql.task.data.dto.TaskStatus
import timber.log.Timber

data class TaskAnswer(
    val status: TaskStatus,
    val expectedResult: List<List<Any>>?,
    val studentResult: List<List<Any>>?,
    val message: String?
) {
    companion object {
        fun map(taskAnswerDto: TaskAnswerDto): TaskAnswer {
            return try {
                // May the Lord forgive me for this
                val expectedResult = taskAnswerDto.refResult?.get(1)?.get(1) as? List<List<Any>>
                val studentResult = taskAnswerDto.studentResult?.get(1)?.get(1) as? List<List<Any>>
                TaskAnswer(
                    taskAnswerDto.status,
                    expectedResult,
                    studentResult,
                    taskAnswerDto.message
                )
            } catch (e: Exception) {
                Timber.e(e)
                TaskAnswer(
                    taskAnswerDto.status,
                    null,
                    null,
                    taskAnswerDto.message
                )
            }
        }
    }
}