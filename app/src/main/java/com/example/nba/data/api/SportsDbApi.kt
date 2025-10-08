package com.example.nba.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheSportsDbApiV1 {
    @GET("{key}/searchplayers.php")
    suspend fun searchPlayers(
        @Path("key") apiKey: String,
        @Query("p") playerName: String
    ): PlayersSearchResponse

    @GET("{key}/searchteams.php")
    suspend fun searchTeams(
        @Path("key") apiKey: String,
        @Query("t") teamName: String
    ): TeamsSearchResponse
}