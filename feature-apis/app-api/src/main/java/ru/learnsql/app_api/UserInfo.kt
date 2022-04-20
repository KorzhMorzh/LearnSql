package ru.learnsql.app_api

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("nickname") val login: String = "",
    @SerializedName("given_name") val firstName: String = "",
    @SerializedName("middle_name") val middleName: String = "",
    @SerializedName("family_name") val lastName: String = "",
    @SerializedName("phone_number") val phone: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("sub") val subject: String = ""
) {
    fun getFullUserName() = listOf(lastName, firstName, middleName)
        .filterNot { it.isEmpty() }
        .joinToString(" ")
}