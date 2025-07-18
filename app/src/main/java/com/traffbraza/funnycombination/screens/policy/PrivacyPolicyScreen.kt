package com.traffbraza.funnycombination.screens.policy

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import com.traffbraza.funnycombination.ui.components.DefaultTopAppBar
import com.traffbraza.funnycombination.ui.theme.JordyBlue
import com.traffbraza.funnycombination.ui.theme.fredokaFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(
    onBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = JordyBlue,
        topBar = {
            DefaultTopAppBar(
                title = stringResource(R.string.privacy_policy),
                onBack = onBack
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.privacy_policy_text),
                fontFamily = fredokaFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray
            )
        }
    }
}
