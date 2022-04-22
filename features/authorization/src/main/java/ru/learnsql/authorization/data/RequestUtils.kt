package ru.learnsql.authorization.data

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR

internal suspend fun <ResultT, ErrorBodyT, ExceptionT> runRequestAndParseErrors(
    errorBodyClass: Class<ErrorBodyT>,
    errorParser: (HttpException, ErrorBodyT) -> ExceptionT?,
    block: suspend () -> ResultT
) where ExceptionT : Throwable = try {
    block()
} catch (ex: HttpException) {
    throw ex.toDomainErrorOrNull(errorBodyClass, errorParser) ?: ex
}

private suspend fun <ErrorBodyT, ExceptionT> HttpException.toDomainErrorOrNull(
    errorBodyClass: Class<ErrorBodyT>,
    errorParser: (HttpException, ErrorBodyT) -> ExceptionT?
): ExceptionT? {
    if (code() !in HTTP_BAD_REQUEST until HTTP_INTERNAL_ERROR) return null

    val body = response()?.errorBody() ?: return null
    val bodyString = withContext(Dispatchers.IO) {
        body.string()
    }

    try {
        return Gson().fromJson(bodyString, errorBodyClass)?.let {
            errorParser(this, it)
        }
    } catch (jsonEx: JsonSyntaxException) {
        addSuppressed(jsonEx)
    }
    return null
}