package com.example.error

import com.google.gson.JsonObject
import io.ktor.http.*


enum class ErrorType(val code: Int, val message: String) {
    Unauthorized(HttpStatusCode.Unauthorized.value, "authorization failed"),
    Forbidden(HttpStatusCode.Forbidden.value, "action forbidden"),
    NotFound(HttpStatusCode.NotFound.value, "not found"),
    InternalServerError(HttpStatusCode.InternalServerError.value, "internal server error"),
    UsernameExists(800, "username is already taken"),
    UserNotFound(801, "user not found"),
    InvalidPassword(802, "invalid password"),
    InvalidToken(802, "invalid access token"),
    PostNotFound(900, "post not found"),
    CommentNotFound(901, "comment not found")
}

fun error(msg: String) : JsonObject {
    return JsonObject().apply {
        this.addProperty("error",msg)
    }
}