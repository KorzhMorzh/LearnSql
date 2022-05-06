package ru.learnsql.appnetwork

data class BaseResponse<T>(
    val count: Int,
    val next: Int?,
    val previous: Int?,
    val results: List<T>
)
