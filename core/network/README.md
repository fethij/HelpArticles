# Network Module

Handles network communication using Ktor Client with `MockEngine` for development purposes

## Available Endpoints
### GET /articles
Returns list of articles, optional `?category=` filter.
### GET /article/{id}
Returns article detail. Use ID `999` to trigger 404 error.

### GET /500
Returns HTTP 500 Internal Server Error.

## How to Trigger 500 Error

**Simply turn off WiFi/cellular data on your device!**

The `getArticles()` method checks network connectivity and automatically routes to the `/500` endpoint when offline:

```kotlin
override suspend fun getArticles(category: String?): List<NetworkArticle> {
    return client
        .takeIf { networkMonitor.isConnected.value }
        ?.get(ARTICLES) { category?.let { parameter("category", it) } }
        ?.body<List<NetworkArticle>>() ?: run {
            client.get("500").body<List<NetworkArticle>>()  // Triggers 500 error
        }
}
```

The MockEngine returns:
```json
{
  "errorCode": "500",
  "errorTitle": "Internal Server Error",
  "errorMessage": "Check back later."
}
```


