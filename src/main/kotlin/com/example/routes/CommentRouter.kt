package com.example.routes

import com.example.extensions.*
import com.example.model.Comment
import com.example.model.User
import com.example.model.response.toResponseList
import com.example.model.response.toResponseObject
import com.example.service.CommentService
import com.example.service.UserService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.util.pipeline.*

object CommentRouter {


    //To get path parameter: this.parameter["key"]
    //To get query parameter this.request.queryParameters["key"]

    fun delete(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely(successCodeOverride = HttpStatusCode.NoContent) {
                val id = getLongParam("id") ?: throw Exception("Parameter id doesn't exist!")
                CommentService.delete(id)
            }
        }
    }

    fun deleteAll(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely(successCodeOverride = HttpStatusCode.NoContent) {
                CommentService.deleteAll()
            }
        }
    }


    fun insert(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                CommentService.insert(receive())
            }
        }
    }

    fun selectAll(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                with(request) {
                    CommentService.all(
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
                CommentService.find(id).toResponseObject()
            }
        }

    }


    fun update(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                val comment = receive<Comment>()
                CommentService.update(comment.id) {
                    this.userName = comment.userName
                    this.userId = comment.userId
                    this.content = comment.content
                }

            }
        }
    }
}