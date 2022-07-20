package com.example.plugins

import com.example.routes.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {

    routing {

        get("/") {
            call.respondText("Hello World!")
        }

        route("/clips") {
            delete("/{id}", ClipRouter.delete())
            delete( ClipRouter.deleteAll())
            get(ClipRouter.selectAll())
            get("/{id}", ClipRouter.selectById())
            post(ClipRouter.insert())
            put(ClipRouter.update())
        }
        route("/comments") {
            delete("/{id}", CommentRouter.delete())
            delete( CommentRouter.deleteAll())
            get(CommentRouter.selectAll())
            get("/{id}", CommentRouter.selectById())
            post(CommentRouter.insert())
            put(CommentRouter.update())
        }

        route("/likes"){
            delete("/{id}", LikeRouter.delete())
            delete( LikeRouter.deleteAll())
            get(LikeRouter.selectAll())
            get("/{id}", LikeRouter.selectById())
            post(LikeRouter.insert())
            put(LikeRouter.update())
        }

        route("/orders"){
            delete("/{id}", OrderRouter.delete())
            delete( OrderRouter.deleteAll())
            get(OrderRouter.selectAll())
            get("/{id}", OrderRouter.selectById())
            post(OrderRouter.insert())
            put(OrderRouter.update())
        }

        route("/rooms") {
            delete("/{id}", RoomRouter.delete())
            delete( RoomRouter.deleteAll())
            get(RoomRouter.selectAll())
            get("/{id}", RoomRouter.selectById())
            post(RoomRouter.insert())
            put(RoomRouter.update())
        }

        route("/users") {
            delete("/{id}", UserRouter.delete())
            delete( UserRouter.deleteAll())
            get(UserRouter.selectAll())
            get("/{id}", UserRouter.selectById())
            post(UserRouter.insert())
            put(UserRouter.update())
        }

    }
}
