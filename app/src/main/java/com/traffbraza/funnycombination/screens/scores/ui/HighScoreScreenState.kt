package com.traffbraza.funnycombination.screens.scores.ui

sealed interface HighScoreScreenState {
    data object Loading : HighScoreScreenState
    data object Error : HighScoreScreenState
    data class Content(val resultsHistory: List<ResultsHistoryItem>) : HighScoreScreenState
}
