package com.example.error


class ParameterNotFoundException(msg: String) : Exception(msg) {
    constructor() : this("Parameter missing!")
}
