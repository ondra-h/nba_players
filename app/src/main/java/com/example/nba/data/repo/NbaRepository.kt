package com.example.nba.data.repo

import com.example.nba.data.api.BallDontLieApi
import com.example.nba.data.mappers.toDomain
import com.example.nba.domain.Player
import com.example.nba.domain.Team
import java.util.concurrent.ConcurrentHashMap

class NbaRepository(private val api: BallDontLieApi, apiKey: String) {
    private val authHeader: String = if (apiKey.startsWith("Bearer ")) apiKey else "Bearer $apiKey"

    private val playerCache = ConcurrentHashMap<Int, Player>()
    private val teamCache = ConcurrentHashMap<Int, Team>()

    fun playersPagingSource(pageSize: Int) = PlayerPagingSource(api, authHeader, pageSize)

    suspend fun getPlayer(id: Int): Player {
        playerCache[id]?.let { return it }
        val p = api.getPlayer(authHeader, id).data.toDomain()
        playerCache[id] = p
        p.team?.let { teamCache[it.id] = it }
        return p
    }

    suspend fun getTeam(id: Int): Team {
        teamCache[id]?.let { return it }
        val t = api.getTeam(authHeader, id).data.toDomain()
        teamCache[id] = t
        return t
    }

    fun primePlayerFromList(p: Player) { playerCache[p.id] = p; p.team?.let { teamCache[it.id] = it } }
}