package com.example.nba.data.mappers

import com.example.nba.data.api.PlayerDto
import com.example.nba.data.api.TeamDto
import com.example.nba.domain.Player
import com.example.nba.domain.Team

fun TeamDto.toDomain() = Team(
    id = id,
    name = name.orEmpty(),
    fullName = full_name.orEmpty(),
    city = city.orEmpty(),
    abbreviation = abbreviation.orEmpty(),
    conference = conference,
    division = division
)

fun PlayerDto.toDomain() = Player(
    id = id,
    firstName = first_name,
    lastName = last_name,
    position = position,
    team = team?.toDomain(),
    height = height,
    weight = weight,
    jerseyNumber = jersey_number,
    college = college,
    country = country,
    draftYear = draft_year,
    draftRound = draft_round,
    draftNumber = draft_number
)