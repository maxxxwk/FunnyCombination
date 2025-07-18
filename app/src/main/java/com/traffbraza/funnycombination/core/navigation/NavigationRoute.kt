package com.traffbraza.funnycombination.core.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    @Serializable
    data object MainScreen : NavigationRoute

    @Serializable
    data object PrivacyPolicyScreen : NavigationRoute

    @Serializable
    data object HighScoreScreen : NavigationRoute

    @Serializable
    data object GameScreen : NavigationRoute

    @Serializable
    data class GameOverDialog(
        val score: Int,
        val isNewRecord: Boolean
    ) : NavigationRoute
}
