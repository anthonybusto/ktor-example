package com.example.extensions

import org.jetbrains.exposed.sql.SizedIterable

fun <T> SizedIterable<T>.paginate(page: Int = 1, pageSize: Int = 20): SizedIterable<T> {
    return limit(n = pageSize, offset = ((page - 1) * pageSize))
}


fun <T> SizedIterable<T>.isNotEmpty() = !empty()