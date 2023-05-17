package com.example.testappshtr

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.testappshtr.service.FoodService
import com.example.testappshtr.service.FoodServiceImpl
import com.example.testappshtr.ui.list.MainViewModel
import com.example.testappshtr.ui.list.ProductsListComposable
import com.example.testappshtr.ui.theme.TestAppShtrTheme
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/*
* 1. Необходимо получить список продуктов (ниже пример получения списка категорий)
* 2. Отсоритровать его по возрастанию поля newPrice
* 3. Отобразить список "Name NewPrice"
*
* Доп задания
* 1. Вывеести значение "Name Цена:NewPrice"
* 2. Написать тест, если список пустой, ошибка загрузки продуктов
* 3. Если список пустой, выбрасывать ошибку загрузки продуктов
*
* Request
* {{baseUrl}}/menu_product?companyUuid={{companyUuid}}
*
* base url https://food-delivery-api-bunbeauty.herokuapp.com/
* companyUuid 7416dba5-2825-4fe3-abfb-1494a5e2bf99
*
*
* https://food-delivery-api-bunbeauty.herokuapp.com/menu_product?companyUuid=7416dba5-2825-4fe3-abfb-1494a5e2bf99
* Json
*  {
    "count": 82,
    "results": [
        {
            "uuid": "486af622-f0f9-4d16-ae8b-b052fedfd61a",
            "name": "Люля-кебаб из свинины",
            "newPrice": 200
        }
      ]
    }
* Response
* "uuid": "486af622-f0f9-4d16-ae8b-b052fedfd61a",
* "name": "Люля-кебаб из свинины",
* "newPrice": 200,
*
*
* */

class MainActivity : ComponentActivity() {

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val client = HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                        encodeDefaults = true
                    }
                )
            }
        }

        val service: FoodService = FoodServiceImpl(client)
        val viewModel = MainViewModel(service)

        setContent {
            TestAppShtrTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ProductsListComposable(viewModel)
                }
            }
        }
    }
}
