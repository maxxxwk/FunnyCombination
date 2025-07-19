package com.traffbraza.funnycombination.screens.game.models

import com.traffbraza.funnycombination.screens.game.engine.GameEngine
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

sealed interface GameScreenState {
    data object Idle : GameScreenState

    data class Demonstration(
        val level: Int,
        val sequence: List<Emoji>,
        val lastVisibleElementIndex: Int
    ) : GameScreenState

    data class PlayerInput(
        val level: Int,
        val playerInput: List<Emoji>,
        val sequenceSize: Int
    ) : GameScreenState

    data class GameOver(
        val score: Int,
        val showGameOverDialogEvent: StateEventWithContent<Boolean> = consumed(),
        val playerInput: List<Emoji>,
        val sequenceSize: Int
    ) : GameScreenState

    fun getCurrentLevel(): Int = when (this) {
        is Demonstration -> level
        is GameOver -> if (score == 0) 1 else score - GameEngine.INITIAL_SEQUENCE_LENGTH + 2
        Idle -> 1
        is PlayerInput -> level
    }
}
