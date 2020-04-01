package com.jamieadkins.acnh.home

import com.jamieadkins.acnh.domain.BugFishSummaryEntity

interface HomeContract {

    interface View {
        fun showLoadingIndicator()
        fun hideLoadingIndicator()
        fun showCrittersAvailableNow(bugFishSummary: BugFishSummaryEntity)
        fun showCrittersGoingSoon(goingSoon: BugFishSummaryEntity)
        fun showCrittersComingSoon(comingSoon: BugFishSummaryEntity)
        fun showCrittersNewThisMonth(newThisMonth: BugFishSummaryEntity)
    }

    interface Presenter {
        fun onAttach(newView: View)
        fun onDetach()
    }
}