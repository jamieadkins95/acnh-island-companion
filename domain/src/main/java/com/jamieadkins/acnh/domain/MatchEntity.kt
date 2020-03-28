package com.jamieadkins.acnh.domain

data class MatchEntity(
    val id: Long,
    val homeTeam: TeamEntity,
    val awayTeam: TeamEntity,
    val outcome: MatchOutcomeEntity
)