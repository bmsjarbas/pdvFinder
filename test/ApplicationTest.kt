package com.zxventures

import com.zxventures.mock.PdvServiceFake
import com.zxventures.service.PdvService
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.junit.experimental.theories.suppliers.TestedOn
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {


    @BeforeTest
    fun before() {
        startKoin { modules(testModules) }
    }

    @AfterTest
    fun after(){
        stopKoin()
    }

    @Test
    fun testReturnsFound() {
        withTestApplication({ module(testing = true)   }) {
            handleRequest(HttpMethod.Get, "/pdv/1").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
    @Test
    fun testReturnsNotFound() {
        withTestApplication({ module(testing = true)   }) {
            handleRequest(HttpMethod.Get, "/pdv/0").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }
    }

    @Test
    fun testReturnsCreated(){
        withTestApplication({ module(testing = true)   }) {
            handleRequest(HttpMethod.Post, "/pdv/") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(" {\n" +
                        "        \"id\": 1, \n" +
                        "        \"tradingName\": \"Adega da Cerveja - Pinheiros\",\n" +
                        "        \"ownerName\": \"Zé da Silva\",\n" +
                        "        \"document\": \"1432132123891/0001\",\n" +
                        "        \"coverageArea\": { \n" +
                        "          \"type\": \"MultiPolygon\", \n" +
                        "          \"coordinates\": [\n" +
                        "            [[[30, 20], [45, 40], [10, 40], [30, 20]]], \n" +
                        "            [[[15, 5], [40, 10], [10, 20], [5, 10], [15, 5]]]\n" +
                        "          ]\n" +
                        "        }, \n" +
                        "        \"address\": { \n" +
                        "          \"type\": \"Point\",\n" +
                        "          \"coordinates\": [-46.57421, -21.785741]\n" +
                        "        } \n" +
                        "    }")

            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
            }

        }
    }



}

val testModules = module{
    singleBy<PdvService, PdvServiceFake>()
}


