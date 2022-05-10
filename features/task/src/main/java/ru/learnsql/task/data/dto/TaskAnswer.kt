package ru.learnsql.task.data.dto

import com.google.gson.annotations.SerializedName

data class TaskAnswerDto(
    @SerializedName("status") val status: TaskStatus,
    @SerializedName("ref_result") val refResult: List<List<Any>>?,
    @SerializedName("student_result") val studentResult: List<List<Any>>?,
    @SerializedName("message") val message: String?,
)

enum class TaskStatus {
    @SerializedName("ok")
    OK,

    @SerializedName("error")
    ERROR
}