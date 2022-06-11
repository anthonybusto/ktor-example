package com.example.error

class EntityNotFoundException(msg: String) : Exception(msg) {
    constructor() : this("Entity not found!")
}