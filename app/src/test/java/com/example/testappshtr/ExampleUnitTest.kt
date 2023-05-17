package com.example.testappshtr

import app.cash.turbine.test
import com.example.testappshtr.models.ListServer
import com.example.testappshtr.models.MenuProductServer
import com.example.testappshtr.service.BackendResult
import com.example.testappshtr.service.FoodService
import com.example.testappshtr.service.FoodServiceImpl
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @MockK
    lateinit var client: HttpClient

    @MockK
    lateinit var httpResponse: HttpResponse

    private lateinit var service: FoodService

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        coEvery { client.get(any()) } returns httpResponse
        coEvery { httpResponse.body<ListServer<MenuProductServer>>() } returns STUB_RESULT

        service = FoodServiceImpl(client)
    }

    @Test
    fun `should throw error`() {
        //mock error
        coEvery { httpResponse.body<ListServer<MenuProductServer>>() } returns STUB_RESULT

        runBlocking {
            val resultFlow = service.getProducts()
            resultFlow.test {
                awaitItem() is BackendResult.Loading
                awaitItem() is BackendResult.Error

                cancelAndIgnoreRemainingEvents()
            }
        }

    }

    companion object {
        val STUB_RESULT = ListServer(
            1,
            listOf(
                MenuProductServer(
                    "11", "test", 1
                )
            )
        )
    }
}