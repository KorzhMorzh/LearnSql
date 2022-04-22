package ru.learnsql.authorization.domain

import ru.learnsql.authorization.data.AuthRepository
import javax.inject.Inject

internal class AuthorizationUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun login(username: String, password: String) {
        authRepository.login(username, password)
    }
}