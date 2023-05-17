package com.example.testappshtr.models

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ListServer<T>(

    @SerialName("count")
    val count: Int,

    @SerialName("results")
    val results: List<T>
)

@Serializable
data class MenuProductServer(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("name")
    val name: String,

    @SerialName("newPrice")
    val newPrice: Int,
)

@Serializable
class CategoryServer(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("name")
    val name: String,

    @SerialName("priority")
    val priority: Int,
)