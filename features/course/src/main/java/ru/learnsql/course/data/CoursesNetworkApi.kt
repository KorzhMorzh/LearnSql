package ru.learnsql.course.data

import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import ru.learnsql.appnetwork.BaseResponse
import ru.learnsql.course.data.dto.CourseDto
import ru.learnsql.course.data.dto.MyCourseDto
import ru.learnsql.course.data.dto.TaskDto

interface CoursesNetworkApi {
    @GET("api/student-course/")
    suspend fun getStudentCourses(): BaseResponse<MyCourseDto>

    @Multipart
    @POST("api/student-course/")
    suspend fun enrollCourse(
        @Part("course") course: RequestBody,
    ): BaseResponse<MyCourseDto>

    @GET("api/courses/")
    suspend fun getAllCourses(): BaseResponse<CourseDto>

    @GET("api/individualroutetasks/{courseId}/")
    suspend fun getCourseDetails(@Path("courseId") courseId: Int): BaseResponse<TaskDto>
}