package com.example.nba.data.repo

import com.example.nba.data.api.TheSportsDbApiV1
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

class ArtRepo(
    private val api: TheSportsDbApiV1,
    private val apiKey: String
) {
    private val playerImg = ConcurrentHashMap<String, String>()
    private val teamLogo  = ConcurrentHashMap<String, String>()

    private val playerMiss = ConcurrentHashMap.newKeySet<String>()
    private val teamMiss   = ConcurrentHashMap.newKeySet<String>()

    suspend fun playerImage(fullName: String): String? {
        val key = fullName.trim()
        playerImg[key]?.let { return it }
        if (playerMiss.contains(key)) return null

        val url = try {
            val list = api.searchPlayers(apiKey, key).player.orEmpty()
            list.firstOrNull()?.strCutout ?: list.firstOrNull()?.strThumb
        } catch (e: Exception) {
            return null
        }

        return if (!url.isNullOrBlank()) {
            playerImg[key] = url
            url
        } else {
            playerMiss.add(key)
            null
        }
    }

    private fun isRaster(url: String?): Boolean {
        if (url.isNullOrBlank()) return false
        val lower = url.substringBefore('?').lowercase()
        return lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".webp")
    }

    suspend fun teamBadge(teamName: String): String? {
        val key = teamName.trim()
        teamLogo[key]?.let { return it }
        if (teamMiss.contains(key)) return null

        val url = try {
            val list = api.searchTeams(apiKey, key).teams.orEmpty()
            val t = list.firstOrNull()
            val badge = t?.strBadge
            val logo  = t?.strLogo
            when {
                isRaster(badge) -> badge
                isRaster(logo)  -> logo
                else -> null
            }
        } catch (e: IOException) {
            return null
        } catch (e: Exception) {
            return null
        }

        return if (!url.isNullOrBlank()) {
            teamLogo[key] = url
            url
        } else {
            teamMiss.add(key)
            null
        }
    }
}