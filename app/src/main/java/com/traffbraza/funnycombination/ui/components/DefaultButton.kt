package com.traffbraza.funnycombination.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.traffbraza.funnycombination.ui.theme.fredokaFontFamily

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.DarkGray
        )
    ) {
        Text(
            text = text,
            fontFamily = fredokaFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
