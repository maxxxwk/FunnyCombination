package com.traffbraza.funnycombination

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.traffbraza.funnycombination.core.navigation.NavigationRoute
import com.traffbraza.funnycombination.screens.game.ui.GameScreen
import com.traffbraza.funnycombination.screens.game.ui.GameScreenNavigator
import com.traffbraza.funnycombination.screens.main.MainScreen
import com.traffbraza.funnycombination.screens.main.MainScreenNavigator
import com.traffbraza.funnycombination.screens.policy.PrivacyPolicyScreen
import com.traffbraza.funnycombination.screens.result.GameOverDialog
import com.traffbraza.funnycombination.screens.result.GameOverDialogNavigator
import com.traffbraza.funnycombination.screens.scores.ui.HighScoreScreen
import com.traffbraza.funnycombination.ui.theme.JordyBlue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val navController = rememberNavController()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(JordyBlue)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = NavigationRoute.MainScreen
                ) {
                    composable<NavigationRoute.MainScreen> {
                        MainScreen(
                            navigator = object : MainScreenNavigator {
                                override fun navigateToGame() {
                                    navController.navigate(NavigationRoute.GameScreen)
                                }

                                override fun navigateToHighScores() {
                                    navController.navigate(NavigationRoute.HighScoreScreen)
                                }

                                override fun navigateToPrivacyPolicy() {
                                    navController.navigate(NavigationRoute.PrivacyPolicyScreen)
                                }

                                override fun exit() {
                                    finish()
                                }
                            }
                        )
                    }
                    composable<NavigationRoute.PrivacyPolicyScreen> {
                        PrivacyPolicyScreen(
                            onBack = {
                                navController.popBackStack(
                                    route = NavigationRoute.MainScreen,
                                    inclusive = false
                                )
                            }
                        )
                    }
                    composable<NavigationRoute.HighScoreScreen> {
                        HighScoreScreen(
                            viewModel = hiltViewModel(),
                            onBack = {
                                navController.popBackStack(
                                    route = NavigationRoute.MainScreen,
                                    inclusive = false
                                )
                            }
                        )
                    }
                    composable<NavigationRoute.GameScreen> {
                        GameScreen(
                            viewModel = hiltViewModel(),
                            navigator = object : GameScreenNavigator {
                                override fun showGameOverDialog(
                                    score: Int,
                                    isNewRecord: Boolean
                                ) {
                                    navController.navigate(
                                        NavigationRoute.GameOverDialog(
                                            score = score,
                                            isNewRecord = isNewRecord
                                        )
                                    )
                                }

                                override fun onBack() {
                                    navController.popBackStack(
                                        route = NavigationRoute.MainScreen,
                                        inclusive = false
                                    )
                                }
                            }
                        )
                    }
                    dialog<NavigationRoute.GameOverDialog> {
                        val route = it.toRoute<NavigationRoute.GameOverDialog>()
                        GameOverDialog(
                            score = route.score,
                            isNewRecord = route.isNewRecord,
                            navigator = object : GameOverDialogNavigator {
                                override fun onBack() {
                                    navController.popBackStack(
                                        route = NavigationRoute.MainScreen,
                                        inclusive = false
                                    )
                                }

                                override fun navigateToNewGame() {
                                    navController.navigate(route = NavigationRoute.GameScreen) {
                                        popUpTo(route = NavigationRoute.MainScreen)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
