package ru.learnsql.profile.data

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ru.learnsql.app_api.UserInfo
import ru.learnsql.app_api.const.TEXT_PLAIN
import ru.learnsql.app_api.state.UserInfoState
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val userInfoState: UserInfoState,
    private val profileNetworkApi: ProfileNetworkApi
) {
    suspend fun updateUserInfo(email: String, firstName: String, lastName: String, groupNumber: Int): UserInfo {
        return profileNetworkApi.updateUserInfo(
            email.toRequestBody(TEXT_PLAIN.toMediaType()),
            firstName.toRequestBody(TEXT_PLAIN.toMediaType()),
            lastName.toRequestBody(TEXT_PLAIN.toMediaType()),
            groupNumber.toString().toRequestBody(TEXT_PLAIN.toMediaType()),
        ).also {
            userInfoState.current = it
        }
    }

    suspend fun editPassword(currentPassword: String, newPassword: String, reNewPassword: String) {
        profileNetworkApi.editPassword(
            currentPassword.toRequestBody(TEXT_PLAIN.toMediaType()),
            newPassword.toRequestBody(TEXT_PLAIN.toMediaType()),
            reNewPassword.toRequestBody(TEXT_PLAIN.toMediaType()),
        )
    }
}