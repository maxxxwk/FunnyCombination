package com.traffbraza.funnycombination.screens.game.engine

import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.traffbraza.funnycombination.di.qualifiers.DispatcherDefault
import com.traffbraza.funnycombination.screens.game.models.Emoji
import com.traffbraza.funnycombination.screens.game.models.GameScreenState
import com.traffbraza.funnycombination.workers.SavingHighScoreWorker
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@Singleton
class GameEngine @Inject constructor(
    private val workManager: WorkManager,
    @DispatcherDefault private val dispatcher: CoroutineDispatcher
) {
    private val mutableStateFlow = MutableStateFlow<GameScreenState>(GameScreenState.Idle)
    val stateFlow = mutableStateFlow.asStateFlow()

    private var scope: CoroutineScope by Delegates.notNull()
    private var demonstrationJob: Job? = null

    private var currentSequence: List<Emoji> = emptyList()

    private var level = 0
    private var lastSuccessfulScore = 0

    fun attachScope(scope: CoroutineScope) {
        this.scope = scope
    }

    fun startNewGame() {
        demonstrationJob?.cancel()
        level = 0
        lastSuccessfulScore = 0
        generateNextLevel()
    }

    fun onEmojiSelected(emoji: Emoji) {
        val currentState = mutableStateFlow.value
        if (currentState !is GameScreenState.PlayerInput) return

        val currentPlayerInput = currentState.playerInput.toMutableList()
        currentPlayerInput.add(emoji)

        if (emoji != currentSequence[currentPlayerInput.size - 1]) {
            mutableStateFlow.value = GameScreenState.GameOver(
                level = currentState.level,
                score = lastSuccessfulScore,
                playerInput = currentState.playerInput,
                sequenceSize = currentSequence.size
            ).also(::startSavingHighScoreWorker)
            return
        }

        mutableStateFlow.value = GameScreenState.PlayerInput(
            level = level,
            playerInput = currentPlayerInput,
            sequenceSize = currentSequence.size
        )

        if (currentPlayerInput.size == currentSequence.size) {
            lastSuccessfulScore = currentSequence.size
            if (level >= MAX_LEVEL) {
                mutableStateFlow.value = GameScreenState.GameOver(
                    level = currentState.level,
                    score = lastSuccessfulScore,
                    playerInput = currentState.playerInput,
                    sequenceSize = currentSequence.size
                ).also(::startSavingHighScoreWorker)
            } else {
                generateNextLevel()
            }
        }
    }

    fun showDialogEventConsumed() {
        (stateFlow.value as? GameScreenState.GameOver)?.let {
            mutableStateFlow.value = it.copy(showGameOverDialogEvent = consumed())
        }
    }


    private fun generateNextLevel() {
        level++
        val sequenceLength = INITIAL_SEQUENCE_LENGTH + level - 1
        currentSequence = List(sequenceLength) { Emoji.entries.random() }
        startSequenceDemonstration()
    }

    private fun startSequenceDemonstration() {
        demonstrationJob?.cancel()
        demonstrationJob = scope.launch(dispatcher) {
            delay(DEMONSTRATION_START_DELAY_MS)
            mutableStateFlow.value = GameScreenState.Demonstration(
                level = level,
                sequence = currentSequence,
                lastVisibleElementIndex = -1
            )
            delay(DEMONSTRATION_PREPARE_DELAY_MS)
            for (i in currentSequence.indices) {
                mutableStateFlow.value = GameScreenState.Demonstration(
                    level = level,
                    sequence = currentSequence,
                    lastVisibleElementIndex = i
                )
                delay(DEMONSTRATION_DELAY_MS)
            }
            mutableStateFlow.value = GameScreenState.PlayerInput(
                level = level,
                playerInput = emptyList(),
                sequenceSize = currentSequence.size
            )
        }
    }

    private fun startSavingHighScoreWorker(gameOverState: GameScreenState.GameOver) {
        val workRequest = OneTimeWorkRequestBuilder<SavingHighScoreWorker>()
            .setInputData(
                workDataOf(
                    SavingHighScoreWorker.SCORE_KEY to gameOverState.score,
                    SavingHighScoreWorker.TIMESTAMP_KEY to System.currentTimeMillis()
                )
            ).setBackoffCriteria(
                backoffPolicy = BackoffPolicy.LINEAR,
                backoffDelay = 500L,
                timeUnit = TimeUnit.MILLISECONDS
            ).build()
        workManager.enqueue(workRequest)
        scope.launch(dispatcher) {
            workManager.getWorkInfoByIdFlow(workRequest.id).filterNotNull()
                .first { it.state.isFinished }.let { workInfo ->
                    if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                        val isNewHighScore = workInfo.outputData.getBoolean(
                            SavingHighScoreWorker.IS_NEW_HIGH_SCORE_KEY,
                            false
                        )
                        mutableStateFlow.value = gameOverState.copy(
                            showGameOverDialogEvent = triggered(isNewHighScore)
                        )
                    } else {
                        mutableStateFlow.value = gameOverState.copy(
                            showGameOverDialogEvent = triggered(false)
                        )
                    }
                }
        }
    }

    companion object {
        const val INITIAL_SEQUENCE_LENGTH = 3
        private const val MAX_LEVEL = 30
        private const val DEMONSTRATION_DELAY_MS = 1000L
        private const val DEMONSTRATION_START_DELAY_MS = 300L
        private const val DEMONSTRATION_PREPARE_DELAY_MS = 100L
    }
}
