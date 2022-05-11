package ru.learnsql.profile.domain

import ru.learnsql.app_api.UserInfo
import ru.learnsql.profile.data.ProfileRepository
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun updateUserInfo(email: String, firstName: String, lastName: String, groupNumber: Int): UserInfo =
        profileRepository.updateUserInfo(email, firstName, lastName, groupNumber)
}