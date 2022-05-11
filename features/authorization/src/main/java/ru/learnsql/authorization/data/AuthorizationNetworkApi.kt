package ru.learnsql.authorization.data

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import ru.learnsql.app_api.UserInfo
import ru.learnsql.app_api.config.OAUTH_CLIENT_ID
import ru.learnsql.app_api.config.OAUTH_CLIENT_SECRET
import ru.learnsql.app_api.const.TEXT_PLAIN
import ru.learnsql.authorization.data.dto.AuthResponse

interface AuthorizationNetworkApi {
    @Multipart
    @POST("social_auth_v2/token")
    suspend fun login(
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody,
        @Part("grant_type") grantType: RequestBody = "password".toRequestBody(TEXT_PLAIN.toMediaType()),
        @Part("client_id") clientId: RequestBody = OAUTH_CLIENT_ID.toRequestBody(TEXT_PLAIN.toMediaType()),
        @Part("client_secret") clientSecret: RequestBody = OAUTH_CLIENT_SECRET.toRequestBody(TEXT_PLAIN.toMediaType()),
    ): AuthResponse

    @GET("auth/users/me/")
    suspend fun getUserInformation(@Header("Authorization") authHeader: String): UserInfo
}