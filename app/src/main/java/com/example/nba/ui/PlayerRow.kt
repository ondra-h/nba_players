package com.example.nba.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.nba.R
import com.example.nba.domain.Player

@Composable
fun PlayerRow(p: Player, onPlayerClick: (Int) -> Unit) {
    val dash = stringResource(R.string.value_dash)
    val sep = stringResource(R.string.separator_bullet)
    Column(
        Modifier.fillMaxWidth().clickable { onPlayerClick(p.id) }.padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "${p.firstName} ${p.lastName}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        val team = p.team?.fullName ?: dash
        val pos = p.position ?: dash
        Text(text = pos + sep + team, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
    Divider()
}