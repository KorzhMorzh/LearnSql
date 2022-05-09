package ru.learnsql.course.domain

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ru.learnsql.app_api.const.TEXT_PLAIN
import ru.learnsql.course.data.CoursesNetworkApi
import javax.inject.Inject

internal class EnrollCourseUseCase @Inject constructor(
    private val coursesNetworkApi: CoursesNetworkApi
) {
    suspend fun enrollCourse(courseId: Int) {
        coursesNetworkApi.enrollCourse(
            courseId.toString().toRequestBody(TEXT_PLAIN.toMediaType()),
        )
    }
}