package ru.learnsql.course.data

import com.google.gson.annotations.SerializedName

data class MyCourseDto(
    @SerializedName("course") val courseId: Int,
    @SerializedName("course_title") val courseTitle: String
)