package com.jamieadkins.acnh.home

import com.jamieadkins.acnh.bugs.BugsCaughtContract
import com.jamieadkins.acnh.domain.BugFishSummaryEntity
import com.jamieadkins.acnh.fish.FishCaughtContract

interface HomeContract {

    interface View {
        fun showLoadingIndicator()
        fun hideLoadingIndicator()
        fun showCrittersAvailableNow(bugFishSummary: BugFishSummaryEntity, rarestCritter: Any?)
        fun showCrittersGoingSoon(goingSoon: BugFishSummaryEntity, rarestCritter: Any?)
        fun showCrittersComingSoon(comingSoon: BugFishSummaryEntity, rarestCritter: Any?)
        fun showCrittersNewThisMonth(newThisMonth: BugFishSummaryEntity, rarestCritter: Any?)
    }

    interface Presenter : FishCaughtContract, BugsCaughtContract {
        fun onAttach(newView: View)
        fun onDetach()
    }
}