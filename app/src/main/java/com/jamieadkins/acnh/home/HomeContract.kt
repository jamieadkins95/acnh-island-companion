package com.jamieadkins.acnh.home

import com.jamieadkins.acnh.domain.BugFishSummaryEntity

interface HomeContract {

    interface View {
        fun showLoadingIndicator()
        fun hideLoadingIndicator()
        fun showCrittersAvailableNow(bugFishSummary: BugFishSummaryEntity, rarestCritter: Any?)
        fun showCrittersGoingSoon(goingSoon: BugFishSummaryEntity, rarestCritter: Any?)
        fun showCrittersComingSoon(comingSoon: BugFishSummaryEntity, rarestCritter: Any?)
        fun showCrittersNewThisMonth(newThisMonth: BugFishSummaryEntity, rarestCritter: Any?)
    }

    interface Presenter {
        fun onAttach(newView: View)
        fun onDetach()
    }
}