package ru.learnsql.task.data.dto

import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("database_description") val dataBaseDescription: String?,
    @SerializedName("database_image") val databaseImage: String?,
    @SerializedName("task_text") val taskText: String,
    @SerializedName("required_words") val requiredWords: String?,
)