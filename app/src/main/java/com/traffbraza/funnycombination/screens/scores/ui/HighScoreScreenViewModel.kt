package com.traffbraza.funnycombination.screens.scores.ui

import androidx.lifecycle.viewModelScope
import com.traffbraza.funnycombination.core.viewmodel.BaseViewModel
import com.traffbraza.funnycombination.screens.scores.data.ResultsHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@HiltViewModel
class HighScoreScreenViewModel @Inject constructor(
    resultsHistoryRepository: ResultsHistoryRepository
) : BaseViewModel<HighScoreScreenState>(
    initialState = HighScoreScreenState.Loading
) {
    init {
        resultsHistoryRepository.getResultsHistoryFlow()
            .onEach { resultsHistory ->
                mutableState.update { HighScoreScreenState.Content(resultsHistory) }
            }.catch {
                mutableState.update { HighScoreScreenState.Error }
            }.launchIn(viewModelScope)
    }
}
