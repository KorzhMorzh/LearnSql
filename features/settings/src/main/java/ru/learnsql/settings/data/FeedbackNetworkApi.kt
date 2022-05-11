package ru.learnsql.settings.data

import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FeedbackNetworkApi {
    @Multipart
    @POST("api/feedback/add")
    suspend fun sendFeedback(
        @Part("subject") subject: RequestBody,
        @Part("message") message: RequestBody,
        @Part("user") user: RequestBody
    )
}