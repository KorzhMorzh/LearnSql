package ru.learnsql.app_api

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("email") val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String
) {
    fun getFullUserName() = listOf(lastName, firstName)
        .filterNot { it.isEmpty() }
        .joinToString(" ")
}