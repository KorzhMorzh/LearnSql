package ru.learnsql.profile.domain

import ru.learnsql.profile.data.ProfileRepository
import javax.inject.Inject

class EditPasswordUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun editPassword(currentPassword: String, newPassword: String, reNewPassword: String) =
        profileRepository.editPassword(currentPassword, newPassword, reNewPassword)
}