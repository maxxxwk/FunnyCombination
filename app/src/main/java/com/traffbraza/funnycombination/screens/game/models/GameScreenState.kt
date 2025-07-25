package com.traffbraza.funnycombination.screens.game.models

import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

sealed interface GameScreenState {
    val level: Int

    data object Idle : GameScreenState {
        override val level: Int = 1
    }

    data class Demonstration(
        override val level: Int,
        val sequence: List<Emoji>,
        val lastVisibleElementIndex: Int
    ) : GameScreenState

    data class PlayerInput(
        override val level: Int,
        val playerInput: List<Emoji>,
        val sequenceSize: Int
    ) : GameScreenState

    data class GameOver(
        override val level: Int,
        val score: Int,
        val showGameOverDialogEvent: StateEventWithContent<Boolean> = consumed(),
        val playerInput: List<Emoji>,
        val sequenceSize: Int
    ) : GameScreenState
}
