package com.traffbraza.funnycombination.screens.game.ui

interface GameScreenNavigator {
    fun showGameOverDialog(score: Int, isNewRecord: Boolean)
    fun onBack()
}
