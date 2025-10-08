package com.example.nba.domain

data class Player(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val position: String?,
    val team: Team?,
    val height: String?,
    val weight: String?,
    val jerseyNumber: String?,
    val college: String?,
    val country: String?,
    val draftYear: Int?,
    val draftRound: Int?,
    val draftNumber: Int?
)