package com.jamieadkins.acnh.domain

sealed class MatchOutcomeEntity {

    object HomeTeamWin : MatchOutcomeEntity()
    object AwayTeamWin : MatchOutcomeEntity()
    object Tie : MatchOutcomeEntity()
    object Undecided : MatchOutcomeEntity()
}