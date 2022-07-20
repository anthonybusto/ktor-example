package com.example.routes

import com.example.extensions.*
import com.example.model.Like
import com.example.model.response.toResponseList
import com.example.model.response.toResponseObject
import com.example.service.LikeService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.util.pipeline.*

object LikeRouter {


    //To get path parameter: this.parameter["key"]
    //To get query parameter this.request.queryParameters["key"]

    fun delete(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely(successCodeOverride = HttpStatusCode.NoContent) {
                val id = getLongParam("id") ?: throw Exception("Parameter id doesn't exist!")
                LikeService.delete(id)
            }
        }
    }

    fun deleteAll(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely(successCodeOverride = HttpStatusCode.NoContent) {
                LikeService.deleteAll()
            }
        }
    }


    fun insert(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                LikeService.insert(receive())
            }
        }
    }

    fun selectAll(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                with(request) {
                    LikeService.all(
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
                LikeService.find(id).toResponseObject()
            }
        }

    }


    fun update(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                val like = receive<Like>()
                LikeService.update(like.id) {
                    this.userName = like.userName
                    this.userId = like.userId
                    this.count = like.count
                }

            }
        }
    }
}