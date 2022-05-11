package ru.learnsql.profile.data.dto

import com.google.gson.annotations.SerializedName

data class StudentGroups(
    @SerializedName("id") val id: Int,
    @SerializedName("organization") val organization: String,
    @SerializedName("period") val period: String,
    @SerializedName("title") val title: String,
)