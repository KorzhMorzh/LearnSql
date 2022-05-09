package ru.learnsql.course.data.dto

import com.google.gson.annotations.SerializedName

data class CourseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("difficulty") val difficulty: Int,
    @SerializedName("title") val title: String,
)