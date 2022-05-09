package ru.learnsql.course.domain.model

import ru.learnsql.course.data.dto.TaskDto

data class TaskList(
    val count: Int,
    val resolvedCount: Int,
    val tasks: List<Task>
)

data class Task(
    val individualTaskId: Int,
    val solution: String?,
    val isResolved: Boolean
) {
    constructor(task: TaskDto) : this(
        individualTaskId = task.taskInSet.id,
        solution = task.solution,
        isResolved = task.status == "1"
    )
}