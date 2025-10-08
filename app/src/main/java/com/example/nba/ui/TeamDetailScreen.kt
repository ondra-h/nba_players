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
import com.example.nba.domain.Team
import com.example.nba.ui.components.BackTopBar
import com.example.nba.ui.components.DetailScaffold
import com.example.nba.ui.components.ErrorBox
import com.example.nba.ui.components.RemoteImageBox
import com.example.nba.ui.components.UiError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@Composable
fun TeamDetailScreen(teamId: Int, onBack: () -> Unit) {
    var team by remember { mutableStateOf<Team?>(null) }
    var error by remember { mutableStateOf<UiError?>(null) }
    var loadKey by remember { mutableStateOf(0) }

    LaunchedEffect(teamId, loadKey) {
        error = null
        try {
            team = withContext(Dispatchers.IO) { ServiceLocator.repo.getTeam(teamId) }
        } catch (_: IOException) {
            team = null; error = UiError.Offline
        } catch (e: HttpException) {
            team = null
            error = if (e.code() == 429) {
                val retryAfter = e.response()?.headers()?.get("Retry-After")?.toIntOrNull()
                UiError.RateLimited(retryAfter)
            } else UiError.Unknown
        } catch (_: Exception) {
            team = null; error = UiError.Unknown
        }
    }

    val title = team?.let { stringResource(R.string.title_team_detail, it.fullName) }
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
            team == null -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            else -> {
                val t = team!!
                Column(
                    Modifier.padding(padding).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    RemoteImageBox(
                        size = 72.dp,
                        placeholderRes = R.drawable.ic_team_placeholder,
                        contentDescriptionRes = R.string.cd_team_logo,
                        reloadKey = t.id
                    ) {
                        ImageUrlResolver.fetchTeamLogo(t.fullName)
                    }

                    InfoRow(stringResource(R.string.label_city), t.city)
                    InfoRow(stringResource(R.string.label_team_abbr), t.abbreviation)
                    InfoRow(stringResource(R.string.label_conference), t.conference ?: dash)
                    InfoRow(stringResource(R.string.label_division), t.division ?: dash)
                }
            }
        }
    }
}
