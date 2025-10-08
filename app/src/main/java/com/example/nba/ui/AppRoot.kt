package com.example.nba.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nba.ui.navigation.Route

@Composable
fun AppRoot() {
    val nav = rememberNavController()
    MaterialTheme {
        Surface {
            NavHost(navController = nav, startDestination = Route.Players.path) {
                composable(Route.Players.path) {
                    PlayersScreen(onPlayerClick = { nav.navigate(Route.Player.create(it)) })
                }
                composable(
                    route = Route.Player.path,
                    arguments = listOf(navArgument("playerId") { type = NavType.IntType })
                ) {
                    val id = it.arguments!!.getInt("playerId")
                    PlayerDetailScreen(
                        playerId = id,
                        onBack = { nav.navigateUp() },
                        onTeamClick = { teamId -> nav.navigate(Route.Team.create(teamId)) }
                    )
                }
                composable(
                    route = Route.Team.path,
                    arguments = listOf(navArgument("teamId") { type = NavType.IntType })
                ) {
                    val id = it.arguments!!.getInt("teamId")
                    TeamDetailScreen(teamId = id, onBack = { nav.navigateUp() })
                }
            }
        }
    }
}