package com.traffbraza.funnycombination.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.traffbraza.funnycombination.ui.theme.JordyBlue
import com.traffbraza.funnycombination.ui.theme.fredokaFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    title: String,
    onBack: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontFamily = fredokaFontFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.DarkGray
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = JordyBlue
        )
    )
}
