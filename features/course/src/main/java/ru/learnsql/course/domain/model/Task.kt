package ru.learnsql.course.domain.model

import ru.learnsql.course.data.dto.TaskDto

data class TaskList(
    val count: Int,
    val resolvedCount: Int,
    val tasks: List<Task>
)

data class Task(
    val taskId: Int,
    val solution: String,
    val isResolved: Boolean,
    val id: Int
) {
    constructor(task: TaskDto) : this(
        taskId = task.taskInSet.id,
        solution = task.solution.orEmpty(),
        isResolved = task.status == "1",
        id = task.id
    )
}