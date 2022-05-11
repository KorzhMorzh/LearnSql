package ru.learnsql.settings.domain

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ru.learnsql.app_api.const.TEXT_PLAIN
import ru.learnsql.settings.data.FeedbackNetworkApi
import javax.inject.Inject
import javax.inject.Named

class SendFeedbackUseCase @Inject constructor(
    private val feedbackNetworkApi: FeedbackNetworkApi,
    @Named("userId") private val userId: Int
) {
    suspend fun sendFeedback(subject: String, message: String) {
        feedbackNetworkApi.sendFeedback(
            subject.toRequestBody(TEXT_PLAIN.toMediaType()),
            message.toRequestBody(TEXT_PLAIN.toMediaType()),
            userId.toString().toRequestBody(TEXT_PLAIN.toMediaType())
        )
    }
}