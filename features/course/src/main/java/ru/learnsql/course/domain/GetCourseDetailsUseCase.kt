package ru.learnsql.course.domain

import ru.learnsql.course.data.CoursesNetworkApi
import ru.learnsql.course.domain.model.Task
import ru.learnsql.course.domain.model.TaskList
import javax.inject.Inject

class GetCourseDetailsUseCase @Inject constructor(
    private val coursesNetworkApi: CoursesNetworkApi
) {
    suspend fun getCourseDetails(courseId: Int): TaskList {
        val response = coursesNetworkApi.getCourseDetails(courseId)
        val tasks = response.results.map(::Task)
        return TaskList(response.count, tasks.count { it.isResolved }, tasks)
    }
}