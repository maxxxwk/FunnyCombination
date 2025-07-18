package com.traffbraza.funnycombination.screens.scores.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.traffbraza.funnycombination.R
import com.traffbraza.funnycombination.ui.components.DefaultTopAppBar
import com.traffbraza.funnycombination.ui.theme.JordyBlue
import com.traffbraza.funnycombination.ui.theme.fredokaFontFamily

@Composable
fun HighScoreScreen(
    viewModel: HighScoreScreenViewModel,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = JordyBlue,
        topBar = {
            DefaultTopAppBar(
                title = stringResource(R.string.high_scores_screen_title),
                onBack = onBack
            )
        }
    ) { innerPaddings ->
        val state by viewModel.state.collectAsStateWithLifecycle()
        when (state) {
            is HighScoreScreenState.Content -> (state as? HighScoreScreenState.Content)?.let {
                HighScoreScreenContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPaddings),
                    content = it
                )
            }

            HighScoreScreenState.Error ->
                HighScoreScreenError(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPaddings)
                )

            HighScoreScreenState.Loading -> HighScoreScreenLoading(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPaddings)
            )
        }
    }
}

@Composable
private fun HighScoreScreenContent(
    modifier: Modifier = Modifier,
    content: HighScoreScreenState.Content
) {
    if (content.resultsHistory.isEmpty()) {
        Box(
            modifier = modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.no_results_yet),
                fontFamily = fredokaFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )
        }
    } else {
        Column(
            modifier = modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.date),
                    fontFamily = fredokaFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Text(
                    text = stringResource(R.string.score),
                    fontFamily = fredokaFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            }
            HorizontalDivider(
                thickness = 2.dp,
                color = Color.DarkGray
            )
            HighScoreScreenList(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                resultsHistory = content.resultsHistory
            )
        }
    }
}

@Composable
private fun HighScoreScreenList(
    modifier: Modifier = Modifier,
    resultsHistory: List<ResultsHistoryItem>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = resultsHistory,
            key = { it.score }
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = JordyBlue
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.DarkGray
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = it.date,
                        fontFamily = fredokaFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                    Text(
                        text = it.score,
                        fontFamily = fredokaFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

@Composable
private fun HighScoreScreenError(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.something_went_wrong),
            fontFamily = fredokaFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray
        )
    }
}

@Composable
private fun HighScoreScreenLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
