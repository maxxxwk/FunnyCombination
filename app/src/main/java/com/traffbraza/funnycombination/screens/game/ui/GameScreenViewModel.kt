package com.traffbraza.funnycombination.screens.game.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.traffbraza.funnycombination.screens.game.engine.GameEngine
import com.traffbraza.funnycombination.screens.game.models.Emoji
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
    private val gameEngine: GameEngine
) : ViewModel() {

    val state = gameEngine.stateFlow

    init {
        gameEngine.attachScope(viewModelScope)
        startNewGame()
    }

    private fun startNewGame() {
        gameEngine.startNewGame()
    }

    fun showDialogEventConsumed() {
        gameEngine.showDialogEventConsumed()
    }

    fun onEmojiSelected(emoji: Emoji) {
        gameEngine.onEmojiSelected(emoji)
    }
}
