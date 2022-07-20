package com.example.routes

import com.example.extensions.*
import com.example.model.Order
import com.example.model.response.toResponseList
import com.example.model.response.toResponseObject
import com.example.service.OrderService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.util.pipeline.*

object OrderRouter {


    //To get path parameter: this.parameter["key"]
    //To get query parameter this.request.queryParameters["key"]

    fun delete(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely(successCodeOverride = HttpStatusCode.NoContent) {
                val id = getLongParam("id") ?: throw Exception("Parameter id doesn't exist!")
                OrderService.delete(id)
            }
        }
    }

    fun deleteAll(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely(successCodeOverride = HttpStatusCode.NoContent) {
                OrderService.deleteAll()
            }
        }
    }


    fun insert(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                OrderService.insert(receive())
            }
        }
    }

    fun selectAll(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                with(request) {
                    OrderService.all(
                        page = pageParameter(),
                        pageSize = pageSizeParameter(),
                        sortOrder = sortOrderParameter()
                    ).toResponseList()
                }
            }

        }
    }


    fun selectById(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                val id = getLongParam("id") ?: throw Exception("Parameter id doesn't exist!")
                //To get path parameter: this.parameter["key"]
                //To get query parameter this.request.queryParameters["key"]
                OrderService.find(id).toResponseObject()
            }
        }

    }


    fun update(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                val order = receive<Order>()
                OrderService.update(order.id) {
                    this.firstName = order.firstName
                    this.lastName = order.lastName
                    this.address = order.address
                    this.city = order.city
                    this.state = order.state
                    this.zip = order.zip
                    this.country = order.country
                    this.total = order.total
                    this.userId = order.userId
                    this.orderDate = order.orderDate
                }

            }
        }
    }
}