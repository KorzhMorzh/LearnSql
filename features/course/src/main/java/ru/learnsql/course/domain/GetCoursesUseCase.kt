package ru.learnsql.course.domain

import ru.learnsql.course.data.CoursesNetworkApi
import ru.learnsql.course.domain.model.Course
import javax.inject.Inject

class GetCoursesUseCase @Inject constructor(
    private val coursesNetworkApi: CoursesNetworkApi
) {
    suspend fun getCourses(): Pair<List<Course>, List<Course>> {
        val allCourses = coursesNetworkApi.getAllCourses().results
        val myCourses = coursesNetworkApi.getStudentCourses().results
        val mappedCurses = allCourses.map {
            Course(
                it,
                myCourses.any { myCourse -> myCourse.courseId == it.id }
            )
        }
        return mappedCurses to mappedCurses.filter { it.isMy }
    }
}