package dev.mama1emon.hat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.mama1emon.hat.ds.theme.HatTheme
import dev.mama1emon.hat.game.GameManager
import dev.mama1emon.hat.navigation.HAT_GRAPH_NAME
import dev.mama1emon.hat.navigation.HatRouter
import dev.mama1emon.hat.navigation.hatGraph
import javax.inject.Inject

@AndroidEntryPoint
class HatActivity : ComponentActivity() {

    @Inject
    lateinit var gameManager: GameManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val activityViewModelStoreOwner = requireNotNull(
                value = LocalViewModelStoreOwner.current,
                lazyMessage = { "ActivityViewModelStoreOwner is null" }
            )

            CompositionLocalProvider(
                LocalActivityViewModelStoreOwner provides activityViewModelStoreOwner,
                LocalGameManager provides gameManager
            ) {
                HatTheme {
                    NavHost(
                        navController = navController,
                        startDestination = HAT_GRAPH_NAME,
                        builder = NavGraphBuilder::hatGraph
                    )
                }

                BackHandler { HatRouter.handleOnBackPressedEvent(navController, gameManager) }

                HatRouter.subscribeOnGameEvent(navController, gameManager)
            }
        }
    }
}

/** Провайдер для [ViewModelStoreOwner] Activity */
val LocalActivityViewModelStoreOwner = compositionLocalOf<ViewModelStoreOwner> {
    error("LocalActivityStoreOwnerProvider value not specified")
}

val LocalGameManager = compositionLocalOf<GameManager> {
    error("LocalGameManager value not specified")
}