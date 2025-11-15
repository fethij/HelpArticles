package com.tewelde.articles

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.overlay.ContentWithOverlays
import com.slack.circuitx.gesturenavigation.GestureNavigationDecorationFactory
import com.tewelde.articles.core.common.di.UiScope
import com.tewelde.articles.core.navigation.ArticlesScreen
import com.tewelde.articles.theme.ArticlesTheme
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn


interface AppUi {
    @Composable
    fun Content(
        onRootPop: () -> Unit,
        modifier: Modifier = Modifier,
    )
}

@Inject
@SingleIn(UiScope::class)
@ContributesBinding(UiScope::class)
class ArticlesApp(
    private val circuit: Circuit,
) : AppUi {

    @Composable
    override fun Content(
        onRootPop: () -> Unit,
        modifier: Modifier,
    ) {
        val backStack = rememberSaveableBackStack(root = ArticlesScreen)
        val navigator = rememberCircuitNavigator(backStack) { onRootPop() }
        CircuitCompositionLocals(circuit) {
            ArticlesTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    ContentWithOverlays {
                        NavigableCircuitContent(
                            modifier = modifier,
                            navigator = navigator,
                            backStack = backStack,
                            decoratorFactory = remember(navigator) {
                                GestureNavigationDecorationFactory(onBackInvoked = navigator::pop)
                            }
                        )
                    }
                }
            }
        }
    }
}