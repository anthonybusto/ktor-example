package com.example.error

import com.example.extensions.ErrorResponse

class APIException(val error: ErrorType) : Exception()

fun APIException.toBaseResponse() = ErrorResponse(errorMessage = error.message, errorCode = error.code)

fun apiException(error: ErrorType) : Nothing = throw APIException(error = error)