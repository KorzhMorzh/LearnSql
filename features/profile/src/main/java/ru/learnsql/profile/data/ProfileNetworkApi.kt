package ru.learnsql.profile.data

import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import ru.learnsql.app_api.UserInfo
import ru.learnsql.appnetwork.BaseResponse
import ru.learnsql.profile.data.dto.StudentGroups

interface ProfileNetworkApi {
    @GET("api/student-groups/")
    suspend fun getStudentGroups(): BaseResponse<StudentGroups>

    @Multipart
    @PATCH("auth/users/me/")
    suspend fun updateUserInfo(
        @Part("email") email: RequestBody,
        @Part("first_name") firstName: RequestBody,
        @Part("last_name") lastName: RequestBody,
        @Part("group_number") groupNumber: RequestBody,
    ): UserInfo

    @Multipart
    @POST("auth/users/set_password/")
    suspend fun editPassword(
        @Part("current_password") currentPassword: RequestBody,
        @Part("new_password") newPassword: RequestBody,
        @Part("re_new_password") reNewPassword: RequestBody,
    )
}