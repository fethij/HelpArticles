package com.tewelde.articles.core.network.ktor

import com.tewelde.articles.core.common.ArticlesAppError
import com.tewelde.articles.core.connectivity.NetworkMonitor
import com.tewelde.articles.core.network.ArticlesNetworkDataSource
import com.tewelde.articles.core.network.model.NetworkArticle
import com.tewelde.articles.core.network.model.NetworkArticleDetail
import com.tewelde.articles.core.network.model.NetworkErrorResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.JsonConvertException
import kotlinx.io.IOException
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

const val ARTICLES = "articles"
const val ARTICLE = "article"

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class KtorArticlesNetworkDataSource(
    private val client: HttpClient,
    val networkMonitor: NetworkMonitor,
) : ArticlesNetworkDataSource {
    override suspend fun getArticles(category: String?): List<NetworkArticle> =
        withErrorSendRequest {
            client.get(ARTICLES) {
                category?.let { parameter("category", it) }
            }.body<List<NetworkArticle>>()
        }

    override suspend fun getDetail(articleId: String): NetworkArticleDetail =
        withErrorSendRequest {
            client.get("$ARTICLE/$articleId")
                .body<NetworkArticleDetail>()
        }

    /**
     * Sends the request if the device is online; otherwise, throws NoInternet error.
     * Catches and classifies all network errors into specific AppError types.
     * @param request The suspend function representing the network request to be executed.
     * @return The result of the network request if successful.
     * @throws ArticlesAppError Classified error based on the failure type.
     */
    private suspend inline fun <reified T> withErrorSendRequest(
        crossinline request: suspend () -> T,
    ): T {
        val connected = networkMonitor.isConnected.value
        if (!connected) {
            throw ArticlesAppError.ConnectivityError.NetworkError(IOException("No internet connection").message)
        }

        return try {
            request()
        } catch (e: ClientRequestException) {
            // 4xx
            throw parseBackendError(e.response, e.response.status.value)
        } catch (e: ServerResponseException) {
            // 5xx
            throw ArticlesAppError.ConnectivityError.ServerError(e.response.status.value)
        } catch (_: ConnectTimeoutException) {
            throw ArticlesAppError.ConnectivityError.Timeout
        } catch (_: SocketTimeoutException) {
            throw ArticlesAppError.ConnectivityError.Timeout
        } catch (e: JsonConvertException) {
            throw ArticlesAppError.SerializationError(e.message)
        } catch (e: IOException) {
            throw ArticlesAppError.ConnectivityError.NetworkError(e.message)
        } catch (e: Exception) {
            throw ArticlesAppError.UnknownError(e)
        }
    }

    /**
     * Parses backend error response to extract errorCode, errorTitle, and errorMessage.
     */
    private suspend fun parseBackendError(
        response: HttpResponse,
        statusCode: Int
    ): ArticlesAppError.BackendErrorArticles {
        return try {
            val errorResponse = response.body<NetworkErrorResponse>()
            ArticlesAppError.BackendErrorArticles(
                errorCode = errorResponse.errorCode,
                errorTitle = errorResponse.errorTitle,
                errorMessage = errorResponse.errorMessage,
                statusCode = statusCode
            )
        } catch (_: Exception) {
            ArticlesAppError.BackendErrorArticles(
                errorCode = "UNKNOWN",
                errorTitle = "Error",
                errorMessage = "An error occurred while processing your request.",
                statusCode = statusCode
            )
        }
    }
}
