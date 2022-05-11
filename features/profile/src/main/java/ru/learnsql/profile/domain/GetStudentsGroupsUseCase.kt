package ru.learnsql.profile.domain

import ru.learnsql.profile.data.ProfileNetworkApi
import javax.inject.Inject

class GetStudentsGroupsUseCase @Inject constructor(
    private val profileNetworkApi: ProfileNetworkApi
) {
    suspend fun getStudentGroups() = profileNetworkApi.getStudentGroups()
}