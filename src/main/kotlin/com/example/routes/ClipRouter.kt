package com.example.routes

import com.example.extensions.*
import com.example.model.Clip
import com.example.model.request.ClipBody
import com.example.model.response.toResponseList
import com.example.model.response.toResponseObject
import com.example.service.ClipService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.util.pipeline.*

object ClipRouter {


    //To get path parameter: this.parameter["key"]
    //To get query parameter this.request.queryParameters["key"]

    fun delete(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely(successCodeOverride = HttpStatusCode.NoContent) {
                val id = getLongParam("id") ?: throw Exception("Parameter id doesn't exist!")
                ClipService.delete(id)
            }
        }
    }

    fun deleteAll(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely(successCodeOverride = HttpStatusCode.NoContent) {
                ClipService.deleteAll()
            }
        }
    }


    fun insert(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                ClipService.insert(receive())
            }
        }
    }

    fun selectAll(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                with(request) {
                    ClipService.all(
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
                ClipService.find(id).toResponseObject()
            }
        }

    }


    fun update(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                val clip = receive<ClipBody>()


                ClipService.update(
                    clip.id
                        ?: throw Exception("Clip body parameter cannot have a nullable id value pass over!")
                ) {
//                    this.userName = clip.userName
                    this.url = clip.url
                    this.likes = clip.likes
                    this.comments = clip.comments
                    this.description = clip.description
                }

            }
        }
    }
}