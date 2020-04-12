package com.jamieadkins.acnh.home.coming

import com.jamieadkins.acnh.domain.BugFishSummaryEntity

interface ComingSoonContract {

    interface View {
        fun showLoadingIndicator()
        fun hideLoadingIndicator()
        fun showCrittersComingSoon(comingSoon: BugFishSummaryEntity)
    }

    interface Presenter {
        fun onAttach(newView: View)
        fun onDetach()
    }
}