package com.traffbraza.funnycombination.screens.result

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.traffbraza.funnycombination.ui.theme.Malibu
import com.traffbraza.funnycombination.ui.theme.Pink
import com.traffbraza.funnycombination.ui.theme.PowderBlue
import com.traffbraza.funnycombination.ui.theme.fredokaFontFamily

@Composable
fun GameOverDialog(
    score: Int,
    isNewRecord: Boolean,
    navigator: GameOverDialogNavigator
) {
    BackHandler(enabled = true, onBack = navigator::onBack)
    Column(
        modifier = Modifier
            .background(
                color = PowderBlue,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.game_over),
            fontFamily = fredokaFontFamily,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        Text(
            text = score.toString(),
            fontFamily = fredokaFontFamily,
            fontSize = 64.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray
        )
        if (isNewRecord) {
            Text(
                text = stringResource(R.string.new_high_score),
                fontFamily = fredokaFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DefaultButton(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.main_menu),
                color = Malibu,
                onClick = navigator::onBack
            )

            DefaultButton(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.play_again),
                color = Pink,
                onClick = navigator::navigateToNewGame
            )
        }
    }
}
