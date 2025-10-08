package com.example.nba.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

@Composable
fun DetailScaffold(
    topBar: @Composable () -> Unit,
    content: @Composable (androidx.compose.foundation.layout.PaddingValues) -> Unit
) {
    Scaffold(
        topBar = topBar,
        contentWindowInsets = WindowInsets.systemBars,
        content = content
    )
}