package com.example.nba.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.nba.R

sealed interface UiError {
    data object Offline : UiError
    data class RateLimited(val retryAfterSec: Int?) : UiError
    data object Unknown : UiError
}

@Composable
fun ErrorBox(
    error: UiError,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (titleRes, bodyText) = when (error) {
        UiError.Offline -> R.string.err_offline_title to stringResource(R.string.err_offline_body)
        is UiError.RateLimited -> {
            val hint = error.retryAfterSec?.let {
                stringResource(R.string.err_429_retry_after, it)
            } ?: stringResource(R.string.err_429_retry_hint)
            R.string.err_429_title to stringResource(R.string.err_429_body, hint)
        }
        UiError.Unknown -> R.string.err_unknown_title to stringResource(R.string.err_unknown_body)
    }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = stringResource(titleRes), style = MaterialTheme.typography.titleMedium)
        Text(text = bodyText, style = MaterialTheme.typography.bodyMedium)
        Button(onClick = onRetry) { Text(stringResource(R.string.action_retry)) }
    }
}
