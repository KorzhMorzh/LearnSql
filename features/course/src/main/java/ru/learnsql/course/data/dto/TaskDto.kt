package ru.learnsql.course.data.dto

import com.google.gson.annotations.SerializedName

data class TaskDto(
    @SerializedName("id") val id: Int,
    @SerializedName("solution") val solution: String?,
    @SerializedName("status") val status: String,
    @SerializedName("task_in_set") val taskInSet: TaskInSet,
    @SerializedName("user_course") val userCourse: Int
)

data class TaskInSet(
    @SerializedName("id") val id: Int,
    @SerializedName("task") val task: Int
)