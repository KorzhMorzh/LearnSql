package ru.learnsql.authorization.data.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class TokenErrorBody(
    @SerializedName("error") val error: String? = null,
    @SerializedName("error_description") val errorDescription: String? = null
)