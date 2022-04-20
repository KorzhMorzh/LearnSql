package ru.learnsql.appnetwork

import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE

object HttpLogging {
    fun getInterceptor(isDebug: Boolean) = HttpLoggingInterceptor().apply {
        level = if (isDebug) BODY else NONE
    }
}