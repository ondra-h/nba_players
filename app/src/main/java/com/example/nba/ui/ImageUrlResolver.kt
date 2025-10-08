package com.example.nba.ui

import com.example.nba.di.ServiceLocator

object ImageUrlResolver {
    suspend fun fetchPlayerHeadshot(fullName: String): String? =
        ServiceLocator.artRepo.playerImage(fullName)

    suspend fun fetchTeamLogo(teamFullName: String): String? =
        ServiceLocator.artRepo.teamBadge(teamFullName)
}