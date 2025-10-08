package com.example.nba.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamDto(
    val id: Int,
    val conference: String?,
    val division: String?,
    val city: String?,
    val name: String?,
    val full_name: String?,
    val abbreviation: String?
)


@JsonClass(generateAdapter = true)
data class PlayerDto(
    val id: Int,
    val first_name: String,
    val last_name: String,
    val position: String?,
    val height: String?,
    val weight: String?,
    val jersey_number: String?,
    val college: String?,
    val country: String?,
    val draft_year: Int?,
    val draft_round: Int?,
    val draft_number: Int?,
    val team: TeamDto?
)


@JsonClass(generateAdapter = true)
data class PagedResponse<T>(val data: List<T>, val meta: Meta?)


@JsonClass(generateAdapter = true)
data class Meta(val next_cursor: Long?, val per_page: Int?)

data class PlayersSearchResponse(val player: List<SdbPlayer>?)
data class SdbPlayer(
    val idPlayer: String?,
    val strPlayer: String?,
    val strTeam: String?,
    val strThumb: String?,
    val strCutout: String?
)

data class TeamsSearchResponse(val teams: List<SdbTeam>?)
data class SdbTeam(
    val idTeam: String?,
    val strTeam: String?,

    @Json(name = "strBadge")
    val strBadge: String?,

    @Json(name = "strLogo")
    val strLogo: String?
)