package dev.mama1emon.hat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.mama1emon.hat.ds.theme.HatTheme
import dev.mama1emon.hat.navigation.HAT_GRAPH_NAME
import dev.mama1emon.hat.navigation.hatGraph

@AndroidEntryPoint
class HatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HatTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = HAT_GRAPH_NAME
                ) {
                    hatGraph()
                }
            }
        }
    }
}
