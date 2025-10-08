package com.example.nba.data.api

import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface BallDontLieApi {
    @GET("players")
    suspend fun getPlayers(
        @Header("Authorization") apiKey: String,
        @Query("per_page") perPage: Int,
        @Query("cursor") cursor: Long? = null
    ): PagedResponse<PlayerDto>

    @GET("players/{id}")
    suspend fun getPlayer(
        @Header("Authorization") apiKey: String,
        @Path("id") id: Int
    ): ResponseWrapper<PlayerDto>

    @GET("teams/{id}")
    suspend fun getTeam(
        @Header("Authorization") apiKey: String,
        @Path("id") id: Int
    ): ResponseWrapper<TeamDto>
}

@JsonClass(generateAdapter = true)
data class ResponseWrapper<T>(val data: T)
