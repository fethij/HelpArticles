package com.tewelde.articles.core.data.store

import org.mobilenativefoundation.store.store5.FetcherResult

fun <T : Any> Result<T>.asFetcherResult(): FetcherResult<T> {
  return when {
    isSuccess -> FetcherResult.Data(getOrThrow(), "api")
    else -> FetcherResult.Error.Exception(exceptionOrNull()!!)
  }
}

inline fun fetcherResult(
    origin: String = "api",
    block: () -> ArticlesStore.Output
): FetcherResult<ArticlesStore.Output> {
    return try {
        FetcherResult.Data(value = block(), origin = origin)
    } catch (e: Throwable) {
        FetcherResult.Error.Exception(e)
    }
}
