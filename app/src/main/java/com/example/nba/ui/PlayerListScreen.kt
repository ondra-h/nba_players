package com.example.nba.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.nba.R
import com.example.nba.di.ServiceLocator
import com.example.nba.ui.components.DetailScaffold
import com.example.nba.ui.components.ErrorBox
import com.example.nba.ui.components.UiError
import com.example.nba.ui.components.toUiError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersScreen(
    onPlayerClick: (Int) -> Unit,
    vm: PlayerListViewModel = viewModel()
) {
    val pagingItems = vm.flow.collectAsLazyPagingItems()

    DetailScaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.title_players)) }) }
    ) { padding ->
        val state = pagingItems.loadState

        when (val refresh = state.refresh) {
            is LoadState.Loading -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is LoadState.Error -> {
                val uiError = refresh.error.toUiError()
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    ErrorBox(
                        error = uiError,
                        onRetry = { pagingItems.retry() }
                    )
                }
            }

            is LoadState.NotLoading -> {
                val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }
                LazyColumn(contentPadding = padding, state = listState) {
                    items(pagingItems.itemCount) { index ->
                        pagingItems[index]?.let { player ->
                            PlayerRow(player) {
                                ServiceLocator.repo.primePlayerFromList(player)
                                onPlayerClick(it)
                            }
                        }
                    }

                    // Footer for APPEND states (load more)
                    when (val append = state.append) {
                        is LoadState.Loading -> item {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) { CircularProgressIndicator() }
                        }

                        is LoadState.Error -> item {
                            val uiError = append.error.toUiError()
                            val footerText = when (uiError) {
                                is UiError.RateLimited -> {
                                    uiError.retryAfterSec?.let {
                                        stringResource(R.string.err_429_retry_after, it)
                                    } ?: stringResource(R.string.err_429_retry_hint)
                                }
                                UiError.Offline -> stringResource(R.string.err_offline_body)
                                UiError.Unknown -> stringResource(R.string.err_unknown_body)
                            }
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = footerText)
                                Spacer(Modifier.height(8.dp))
                                Button(onClick = { pagingItems.retry() }) {
                                    Text(stringResource(R.string.action_retry))
                                }
                            }
                        }

                        else -> Unit
                    }
                }
            }
        }
    }
}
