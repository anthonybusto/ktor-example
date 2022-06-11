package com.example.routes

import com.example.extensions.*
import com.example.model.User
import com.example.model.response.toResponseList
import com.example.model.response.toResponseObject
import com.example.service.UserService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.util.pipeline.*

object UserRouter {


    fun delete(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely(successCodeOverride = HttpStatusCode.NoContent) {
                val id = getLongParam("id") ?: throw Exception("Parameter id doesn't exist!")
                UserService.delete(id)
            }
        }
    }

    fun deleteAll(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely(successCodeOverride = HttpStatusCode.NoContent) {
                UserService.deleteAll()
            }
        }
    }


    fun insert(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                UserService.insert(receive())
            }
        }
    }

    fun selectAll(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                with(request) {
                    UserService.all(
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
                UserService.find(id).toResponseObject()
            }
        }

    }


    fun update(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                val user = receive<User>()
                UserService.update(user.id) {
                    this.userName = user.userName
                    this.firstName = user.firstName
                    this.lastName = user.lastName
                    this.city = user.city
                    this.age = user.age
                }

            }
        }
    }

}