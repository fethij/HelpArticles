package com.tewelde.articles.core.common

/**
 * Sealed class representing all possible errors in the application.
 */
sealed class ArticlesAppError(
    override val message: String,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    /**
     * Connectivity errors: no internet, timeout, 5xx server errors, etc.
     * These are infrastructure/network-level failures.
     */
    sealed class ConnectivityError(message: String, cause: Throwable? = null) : ArticlesAppError(message, cause) {

        /**
         * Request timed out.
         */
        data object Timeout : ConnectivityError(
            "Request timed out. Please try again."
        )

        /**
         * Server error (5xx status codes).
         */
        class ServerError(val statusCode: Int) : ConnectivityError(
            "Server error occurred. Please try again later."
        )

        /**
         * Generic network error (DNS, SSL, connection refused, etc.).
         */
        class NetworkError(cause: String?) : ConnectivityError(
            "Network error occurred. Please check your connection.",
            cause?.let { Exception(it) }
        )
    }

    /**
     * Backend-provided errors with errorCode, errorTitle, and errorMessage.
     * These are business logic errors returned by the API (4xx status codes).
     */
    class BackendErrorArticles(
        val errorCode: String,
        val errorTitle: String,
        val errorMessage: String,
        val statusCode: Int
    ) : ArticlesAppError(errorMessage)

    /**
     * Serialization/deserialization errors.
     */
    class SerializationError(causeMessage: String?) : ArticlesAppError(
        "Failed to process response. Please try again.",
        causeMessage?.let { Exception(it) }
    )

    /**
     * Unknown/unexpected errors.
     */
    class UnknownError(cause: Throwable?) : ArticlesAppError(
        "An unexpected error occurred. Please try again.",
        cause
    )
}
