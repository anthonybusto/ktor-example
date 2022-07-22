package com.example.routes

import com.example.extensions.*
import com.example.model.Room

import com.example.model.response.toResponseList
import com.example.model.response.toResponseObject
import com.example.service.RoomService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.util.pipeline.*

object RoomRouter {


    //To get path parameter: this.parameter["key"]
    //To get query parameter this.request.queryParameters["key"]

    fun delete(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely(successCodeOverride = HttpStatusCode.NoContent) {
                val id = getLongParam("id") ?: throw Exception("Parameter id doesn't exist!")
                RoomService.delete(id)
            }
        }
    }

    fun deleteAll(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely(successCodeOverride = HttpStatusCode.NoContent) {
                RoomService.deleteAll()
            }
        }
    }


    fun insert(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                RoomService.insert(receive())
            }
        }
    }

    fun selectAll(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                with(request) {
                    RoomService.all(
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
                RoomService.find(id).toResponseObject()
            }
        }

    }


    fun update(): PipelineInterceptor<Unit, ApplicationCall> = {
        with(call) {
            executeSafely {
                val room = receive<Room>()
                RoomService.update(room.id) {
//                    this.userName = room.createdByUserName
                    this.channelName = room.channelName
//                    this.userId = room.createdByUserId
                    this.uid = room.uid
                }

            }
        }
    }

}