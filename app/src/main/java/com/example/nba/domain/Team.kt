package com.example.nba.domain

data class Team(
    val id: Int,
    val name: String,
    val fullName: String,
    val city: String,
    val abbreviation: String,
    val conference: String?,
    val division: String?
)