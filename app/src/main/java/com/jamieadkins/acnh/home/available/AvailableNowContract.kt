package com.jamieadkins.acnh.home.available

import com.jamieadkins.acnh.domain.BugFishSummaryEntity

interface AvailableNowContract {

    interface View {
        fun showLoadingIndicator()
        fun hideLoadingIndicator()
        fun showCrittersAvailableNow(bugFishSummary: BugFishSummaryEntity)
    }

    interface Presenter {
        fun onAttach(newView: View)
        fun onDetach()
    }
}