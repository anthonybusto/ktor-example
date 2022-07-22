package com.example.extensions

import io.ktor.http.*
import io.ktor.request.*
import org.jetbrains.exposed.sql.SortOrder

private const val PAGE_SIZE = 20

fun Parameters.sortOrder(): SortOrder = this["sort"]
    ?.uppercase()
    ?.takeIf { it == SortOrder.ASC.name || it == SortOrder.DESC.name }
    ?.let(SortOrder::valueOf)
    ?: SortOrder.ASC

fun ApplicationRequest.sortOrderParameter(): SortOrder = this.queryParameters.sortOrder()

fun Parameters.page(): Int? = this["page"]?.toInt()

fun ApplicationRequest.pageParameter(): Int = this.queryParameters.page() ?: 1

fun Parameters.pageSize(): Int = this["pageSize"]?.toInt() ?: PAGE_SIZE

fun ApplicationRequest.pageSizeParameter(): Int = this.queryParameters.pageSize()
