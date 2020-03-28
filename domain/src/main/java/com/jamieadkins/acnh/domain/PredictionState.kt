package com.jamieadkins.acnh.domain

data class PredictionState(
    val season: Int,
    val gameweeks: List<GameWeekEntity>
)
