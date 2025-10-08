package com.example.nba.ui.navigation

sealed class Route(val path: String) {
    data object Players : Route("players")
    data object Player : Route("player/{playerId}") {
        fun create(playerId: Int) = "player/$playerId"
    }
    data object Team : Route("team/{teamId}") {
        fun create(teamId: Int) = "team/$teamId"
    }
}