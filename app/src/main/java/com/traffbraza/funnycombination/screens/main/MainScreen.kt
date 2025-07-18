package com.traffbraza.funnycombination.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traffbraza.funnycombination.R
import com.traffbraza.funnycombination.ui.components.DefaultButton
import com.traffbraza.funnycombination.ui.theme.LightGreen
import com.traffbraza.funnycombination.ui.theme.fredokaFontFamily

@Composable
fun MainScreen(
    navigator: MainScreenNavigator
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            fontFamily = fredokaFontFamily,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DefaultButton(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.play),
                color = LightGreen,
                onClick = navigator::navigateToGame
            )
            DefaultButton(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.high_score),
                color = LightGreen,
                onClick = navigator::navigateToHighScores
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DefaultButton(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.privacy_policy),
                color = LightGreen,
                onClick = navigator::navigateToPrivacyPolicy
            )
            DefaultButton(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.exit),
                color = LightGreen,
                onClick = navigator::exit
            )
        }
    }
}
