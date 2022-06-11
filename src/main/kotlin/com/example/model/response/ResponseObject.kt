package com.example.model.response


import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import org.json.simple.JSONObject

@Serializable
class ResponseObject<out T>(@SerializedName("response") val model: T?) {
    override fun toString(): String = GsonBuilder().setPrettyPrinting().create().toJson(model)
}

@Serializable
class ResponseList<out T>(@SerializedName("response") val list: List<T>) {
    override fun toString(): String = GsonBuilder().setPrettyPrinting().create().toJson(list)
}


fun <T> T.toResponseObject() = JSONObject(mapOf("response" to this))


fun <T> List<T>.toResponseList() = JSONObject(
    mapOf(
        "count" to size,
        "response" to this
    )
)
