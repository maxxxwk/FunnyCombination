package com.traffbraza.funnycombination.screens.game.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.traffbraza.funnycombination.R
import com.traffbraza.funnycombination.screens.game.models.Emoji
import com.traffbraza.funnycombination.screens.game.models.GameScreenState
import com.traffbraza.funnycombination.ui.components.DefaultTopAppBar
import com.traffbraza.funnycombination.ui.theme.JordyBlue
import de.palm.composestateevents.EventEffect

@Composable
fun GameScreen(
    viewModel: GameScreenViewModel,
    navigator: GameScreenNavigator
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.startNewGame() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = JordyBlue,
        topBar = {
            DefaultTopAppBar(
                title = stringResource(
                    R.string.game_screen_title,
                    state.getCurrentLevel()
                ),
                onBack = navigator::onBack
            )
        }
    ) { innerPaddings ->
        when (state) {
            is GameScreenState.Demonstration -> (state as? GameScreenState.Demonstration)?.let {
                GameBoard(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPaddings)
                        .padding(16.dp),
                    emojis = it.sequence.subList(0, it.activeIndex + 1),
                    enabledInput = false,
                    onEmojiSelected = viewModel::onEmojiSelected
                )
            }

            is GameScreenState.GameOver -> (state as? GameScreenState.GameOver)?.let { gameOverState ->
                EventEffect(
                    event = gameOverState.showGameOverDialogEvent,
                    onConsumed = viewModel::showDialogEventConsumed,
                    action = {
                        navigator.showGameOverDialog(
                            score = gameOverState.score,
                            isNewRecord = it
                        )
                    }
                )

                GameBoard(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPaddings)
                        .padding(16.dp),
                    emojis = gameOverState.playerInput,
                    enabledInput = false,
                    onEmojiSelected = viewModel::onEmojiSelected
                )
            }

            GameScreenState.Idle -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            is GameScreenState.PlayerInput -> (state as? GameScreenState.PlayerInput)?.let {
                GameBoard(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPaddings)
                        .padding(16.dp),
                    emojis = it.playerInput,
                    enabledInput = true,
                    onEmojiSelected = viewModel::onEmojiSelected
                )
            }
        }
    }
}

@Composable
private fun GameBoard(
    modifier: Modifier = Modifier,
    emojis: List<Emoji>,
    enabledInput: Boolean,
    onEmojiSelected: (Emoji) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Emojis(
            modifier = Modifier.fillMaxWidth(),
            emojis = emojis
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        InputPanel(
            modifier = Modifier.fillMaxWidth(),
            isActive = enabledInput,
            onClick = onEmojiSelected
        )
    }
}


@Composable
private fun Emojis(
    modifier: Modifier = Modifier,
    emojis: List<Emoji>
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        itemVerticalAlignment = Alignment.CenterVertically
    ) {
        emojis.forEach { emoji ->
            AnimatedVisibility(
                visible = true,
                enter = scaleIn() + fadeIn()
            ) {
                Text(
                    text = emoji.icon,
                    fontSize = 32.sp
                )
            }
        }
    }
}

@Composable
private fun InputPanel(
    modifier: Modifier = Modifier,
    isActive: Boolean,
    onClick: (Emoji) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Emoji.entries.forEach { emoji ->
            val shape = RoundedCornerShape(8.dp)
            Box(
                modifier = Modifier
                    .background(
                        color = Color.LightGray,
                        shape = shape
                    )
                    .border(
                        width = 1.dp,
                        color = Color.DarkGray,
                        shape = shape
                    )
                    .clickable(
                        enabled = isActive,
                        onClick = { onClick(emoji) }
                    )
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = emoji.icon,
                    fontSize = 32.sp
                )
            }
            Spacer(modifier = Modifier.width(2.dp))
        }
    }
}
