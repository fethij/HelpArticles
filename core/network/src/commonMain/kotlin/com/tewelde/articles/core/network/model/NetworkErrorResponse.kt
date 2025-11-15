package com.tewelde.articles.core.network.model

import kotlinx.serialization.Serializable

/**
 * Network model for backend error responses.
 * Backend returns errors with errorCode, errorTitle, and errorMessage fields.
 */
@Serializable
data class NetworkErrorResponse(
    val errorCode: String,
    val errorTitle: String,
    val errorMessage: String
)
