package ru.learnsql.course.data

import retrofit2.http.GET
import ru.learnsql.appnetwork.BaseResponse

interface CoursesNetworkApi {
    @GET("api/student-course/")
    suspend fun getStudentCourses(): BaseResponse<MyCourseDto>

    @GET("api/courses/")
    suspend fun getAllCourses(): BaseResponse<CourseDto>
}