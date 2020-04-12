package com.jamieadkins.acnh.home.going

import com.jamieadkins.acnh.domain.BugFishSummaryEntity

interface GoingSoonContract {

    interface View {
        fun showLoadingIndicator()
        fun hideLoadingIndicator()
        fun showCrittersGoingSoon(goingSoon: BugFishSummaryEntity)
    }

    interface Presenter {
        fun onAttach(newView: View)
        fun onDetach()
    }
}