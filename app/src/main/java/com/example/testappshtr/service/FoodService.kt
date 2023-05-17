package com.example.testappshtr.service

import android.util.Log
import com.example.testappshtr.models.ListServer
import com.example.testappshtr.models.MenuProductServer
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface FoodService {
    suspend fun getProducts(): Flow<BackendResult>
}

class FoodServiceImpl(
    private val client: HttpClient,
) : FoodService {

    override suspend fun getProducts() = flow {
        emit(BackendResult.Loading)

        try {
            val result = client
                .get("https://food-delivery-api-bunbeauty.herokuapp.com/menu_product") {
                    parameter("companyUuid", "7416dba5-2825-4fe3-abfb-1494a5e2bf99")
                }
                .body<ListServer<MenuProductServer>>()
                .results
                .sortedBy { it.newPrice }

            kotlinx.coroutines.delay(2000)

            emit(BackendResult.Complete(result))

        } catch (e: java.lang.Exception) {
            Log.e("AVX", "error", e)
            emit(BackendResult.Error(e.message.orEmpty()))
        }
    }
}

sealed interface BackendResult {
    object Initial : BackendResult
    object Loading : BackendResult
    data class Complete(val data: List<MenuProductServer>) : BackendResult
    data class Error(val text: String) : BackendResult
}