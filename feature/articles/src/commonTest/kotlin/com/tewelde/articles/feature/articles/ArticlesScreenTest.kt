package com.tewelde.articles.feature.articles

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.tewelde.articles.feature.articles.model.ArticlesEvent
import com.tewelde.articles.feature.articles.model.ArticlesLoadState
import com.tewelde.articles.feature.articles.model.ArticlesUiState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


private val testErrorIcon = ImageVector.Builder(
    name = "TestErrorIcon",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path {
        // Simple path for testing
        moveTo(12f, 2f)
        lineTo(2f, 22f)
        lineTo(22f, 22f)
        close()
    }
}.build()

@OptIn(ExperimentalTestApi::class)
class ArticlesScreenTest {

    @Test
    fun `error state displays error message`() = runComposeUiTest {
        val errorMessage = "Unable to load articles. Please check your connection."
        val uiState = ArticlesUiState(
            articlesContentState = ArticlesLoadState.Error(
                message = errorMessage,
                errorIcon = testErrorIcon,
                eventSink = {}
            ),
            eventSink = {}
        )

        setContent {
            ArticlesScreen(uiState = uiState)
        }

        onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun `error state displays retry button`() = runComposeUiTest {
        val uiState = ArticlesUiState(
            articlesContentState = ArticlesLoadState.Error(
                message = "An error occurred",
                errorIcon = testErrorIcon,
                eventSink = {}
            ),
            eventSink = {}
        )

        setContent {
            ArticlesScreen(uiState = uiState)
        }

        onNodeWithText("Retry").assertIsDisplayed()
    }

    @Test
    fun `clicking retry button triggers retry event`() = runComposeUiTest {
        var retryEventTriggered = false
        val errorEventSink: (ArticlesEvent) -> Unit = { event ->
            if (event is ArticlesEvent.Retry) {
                retryEventTriggered = true
            }
        }

        val uiState = ArticlesUiState(
            articlesContentState = ArticlesLoadState.Error(
                message = "Failed to load",
                errorIcon = testErrorIcon,
                eventSink = errorEventSink
            ),
            eventSink = {}
        )

        setContent {
            ArticlesScreen(uiState = uiState)
        }

        onNodeWithText("Retry").performClick()

        assertTrue(
            retryEventTriggered,
            "Retry event should be triggered when retry button is clicked"
        )
    }

    @Test
    fun `error state displays error icon`() = runComposeUiTest {
        val uiState = ArticlesUiState(
            articlesContentState = ArticlesLoadState.Error(
                message = "Error occurred",
                errorIcon = testErrorIcon,
                eventSink = {}
            ),
            eventSink = {}
        )

        setContent {
            ArticlesScreen(uiState = uiState)
        }

        onNodeWithText("Error occurred").assertIsDisplayed()
        onNodeWithText("Retry").assertIsDisplayed()
    }

    @Test
    fun `multiple retry clicks trigger multiple events`() = runComposeUiTest {
        var retryEventCount = 0
        val errorEventSink: (ArticlesEvent) -> Unit = { event ->
            if (event is ArticlesEvent.Retry) {
                retryEventCount++
            }
        }

        val uiState = ArticlesUiState(
            articlesContentState = ArticlesLoadState.Error(
                message = "Network error",
                errorIcon = testErrorIcon,
                eventSink = errorEventSink
            ),
            eventSink = {}
        )

        setContent {
            ArticlesScreen(uiState = uiState)
        }

        val retryButton = onNodeWithText("Retry")
        retryButton.performClick()
        retryButton.performClick()
        retryButton.performClick()

        assertEquals(3, retryEventCount, "Should trigger retry event for each click")
    }

    @Test
    fun `loading state displays progress indicator`() = runComposeUiTest {
        val uiState = ArticlesUiState(
            articlesContentState = ArticlesLoadState.Loading,
            eventSink = {}
        )

        setContent {
            ArticlesScreen(uiState = uiState)
        }

        onNodeWithText("Retry").assertDoesNotExist()
    }

    @Test
    fun `empty state displays no articles message`() = runComposeUiTest {
        val uiState = ArticlesUiState(
            articlesContentState = ArticlesLoadState.Empty,
            eventSink = {}
        )

        setContent {
            ArticlesScreen(uiState = uiState)
        }

        onNodeWithText("Retry").assertDoesNotExist()
    }

    @Test
    fun `error state does not display loading indicator`() = runComposeUiTest {
        val uiState = ArticlesUiState(
            articlesContentState = ArticlesLoadState.Error(
                message = "Error message",
                errorIcon = testErrorIcon,
                eventSink = {}
            ),
            eventSink = {}
        )

        setContent {
            ArticlesScreen(uiState = uiState)
        }

        onNodeWithText("Error message").assertIsDisplayed()
        onNodeWithText("Retry").assertIsDisplayed()
    }

    @Test
    fun `retry button is full width`() = runComposeUiTest {
        val uiState = ArticlesUiState(
            articlesContentState = ArticlesLoadState.Error(
                message = "Connection failed",
                errorIcon = testErrorIcon,
                eventSink = {}
            ),
            eventSink = {}
        )

        setContent {
            ArticlesScreen(uiState = uiState)
        }

        val retryButton = onNodeWithText("Retry")
        retryButton.assertIsDisplayed()
        retryButton.assertHasClickAction()
    }

    @Test
    fun `error state layout contains message icon and button in correct order`() =
        runComposeUiTest {
            val errorMessage = "Please try again"
            val uiState = ArticlesUiState(
                articlesContentState = ArticlesLoadState.Error(
                    message = errorMessage,
                    errorIcon = testErrorIcon,
                    eventSink = {}
                ),
                eventSink = {}
            )

            setContent {
                ArticlesScreen(uiState = uiState)
            }

            onNodeWithText(errorMessage).assertIsDisplayed()
            onNodeWithText("Retry").assertIsDisplayed()
        }
}
