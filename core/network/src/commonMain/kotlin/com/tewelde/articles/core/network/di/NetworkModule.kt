package com.tewelde.articles.core.network.di

import com.tewelde.articles.core.network.data.MockArticleData
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
interface NetworkModule {

    @Provides
    @SingleIn(AppScope::class)
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
        prettyPrint = true
    }

    @Provides
    @SingleIn(AppScope::class)
    fun provideAuthorizedHttpClient(
        json: Json
    ): HttpClient = HttpClient(MockEngine) {
        install(ContentNegotiation) {
            json(json)
        }
        engine {
            addHandler { request ->
                val path = request.url.encodedPath
                when {
                    path == "/articles" -> {
                        val category = request.url.parameters["category"]

                        respond(
                            content = MockArticleData.getArticleListJson(category),
                            status = HttpStatusCode.OK,
                            headers = headersOf(
                                HttpHeaders.ContentType,
                                ContentType.Application.Json.toString()
                            )
                        )
                    }

                    path.startsWith("/article/") -> {
                        when (val id = request.url.segments.last()) {
                            "999" -> respond(
                                content = """
                                {
                                  "errorCode": "ARTICLE_NOT_FOUND",
                                  "errorTitle": "Article Not Found",
                                  "errorMessage": "The requested help article does not exist or has been removed."
                                }
                            """.trimIndent(),
                                status = HttpStatusCode.NotFound,
                                headers = headersOf(
                                    HttpHeaders.ContentType,
                                    ContentType.Application.Json.toString()
                                )
                            )

                            else -> respond(
                                content = MockArticleData.getArticleDetailJson(id),
                                status = HttpStatusCode.OK,
                                headers = headersOf(
                                    HttpHeaders.ContentType,
                                    ContentType.Application.Json.toString()
                                )
                            )
                        }
                    }

                    else -> {
                        error("Unhandled ${request.url.encodedPath}")
                    }
                }
            }
        }
    }
}