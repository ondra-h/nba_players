package com.example.nba.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.nba.R
import com.example.nba.di.ServiceLocator
import com.example.nba.domain.Player
import com.example.nba.ui.components.BackTopBar
import com.example.nba.ui.components.DetailScaffold
import com.example.nba.ui.components.ErrorBox
import com.example.nba.ui.components.RemoteImageBox
import com.example.nba.ui.components.UiError
import com.example.nba.ui.components.toUiError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PlayerDetailScreen(
    playerId: Int,
    onBack: () -> Unit,
    onTeamClick: (Int) -> Unit
) {
    var player by remember { mutableStateOf<Player?>(null) }
    var error by remember { mutableStateOf<UiError?>(null) }
    var loadKey by remember { mutableStateOf(0) }

    LaunchedEffect(playerId, loadKey) {
        error = null
        try {
            player = withContext(Dispatchers.IO) { ServiceLocator.repo.getPlayer(playerId) }
        } catch (t: Throwable) {
            player = null
            error = t.toUiError()
        }
    }

    val title = player?.let { stringResource(R.string.title_player_detail, it.firstName, it.lastName) }
        ?: stringResource(R.string.loading)
    val dash = stringResource(R.string.value_dash)

    DetailScaffold(
        topBar = { BackTopBar(title, onBack) }
    ) { padding ->
        when {
            error != null -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    ErrorBox(error = error!!, onRetry = { loadKey++ })
                }
            }
            player == null -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            else -> {
                val p = player!!
                Column(
                    Modifier.padding(padding).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    RemoteImageBox(
                        size = 120.dp,
                        placeholderRes = R.drawable.ic_player_placeholder,
                        contentDescriptionRes = R.string.cd_player_photo,
                        reloadKey = p.id
                    ) {
                        ImageUrlResolver.fetchPlayerHeadshot("${p.firstName} ${p.lastName}")
                    }

                    InfoRow(stringResource(R.string.label_position), p.position ?: dash)
                    InfoRow(
                        stringResource(R.string.label_team),
                        p.team?.fullName ?: dash,
                        onClick = p.team?.let { { onTeamClick(it.id) } }
                    )
                    InfoRow(stringResource(R.string.label_jersey), p.jerseyNumber ?: dash)
                    InfoRow(stringResource(R.string.label_height), p.height ?: dash)
                    InfoRow(stringResource(R.string.label_weight), p.weight ?: dash)
                    InfoRow(stringResource(R.string.label_college), p.college ?: dash)
                    InfoRow(stringResource(R.string.label_country), p.country ?: dash)
                    InfoRow(
                        stringResource(R.string.label_draft),
                        buildString {
                            append(p.draftYear?.toString() ?: dash)
                            if (p.draftRound != null) append(stringResource(R.string.separator_bullet)).append("round ").append(p.draftRound)
                            if (p.draftNumber != null) append(stringResource(R.string.separator_bullet)).append("pick ").append(p.draftNumber)
                        }
                    )
                }
            }
        }
    }
}
