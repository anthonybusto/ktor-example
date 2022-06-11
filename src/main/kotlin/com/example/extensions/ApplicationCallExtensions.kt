package com.example.extensions

import com.example.error.ErrorType
import com.google.gson.GsonBuilder
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import kotlinx.serialization.Serializable

suspend inline fun <reified T : Any> ApplicationCall.executeSafely(
    successCodeOverride: HttpStatusCode = HttpStatusCode.OK,
    errorCodeOverride: HttpStatusCode? = null,
    fetch: () -> T
) {
    runCatching(fetch)
        .onSuccess {
            respond(
                status = successCodeOverride,
                message = it
            )
        }
        .onFailure {
            val errorCode = errorCodeOverride ?: HttpStatusCode.InternalServerError
            respond(
                status = errorCode,
                message = ErrorResponse(
                    errorCode = errorCode.value,
                    errorMessage = it.localizedMessage
                )
            )
        }
}


fun ApplicationCall.getLongParam(name: String): Long? = this.parameters[name]?.toLongOrNull()
fun ApplicationCall.getIntParam(name: String): Int? = this.parameters[name]?.toIntOrNull()
fun ApplicationCall.getStringParam(name: String): String? = this.parameters[name]

@Serializable
open class ErrorResponse (val errorMessage: String? = null, val errorCode: Int? = null){
    override fun toString(): String =
        GsonBuilder().setPrettyPrinting().create().toJson(this)

    companion object {
        fun of(errorType: ErrorType) = ErrorResponse(errorType.message, errorType.code)
    }
}



