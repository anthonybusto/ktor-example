package com.example.plugins

import com.example.routes.UserRouter
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {

    routing {

        get("/") {
            call.respondText("Hello World!")
        }

        route("/user") {
            delete("/{id}", UserRouter.delete())
            delete( UserRouter.deleteAll())
            get(UserRouter.selectAll())
            get("/{id}", UserRouter.selectById())
            post(UserRouter.insert())
            put(UserRouter.update())
        }

    }
}
