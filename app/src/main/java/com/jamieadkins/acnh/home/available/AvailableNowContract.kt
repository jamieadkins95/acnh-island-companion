package com.jamieadkins.acnh.home.available

import com.jamieadkins.acnh.domain.BugFishSummaryEntity
import com.jamieadkins.acnh.fish.FishCaughtContract

interface AvailableNowContract {

    interface View {
        fun showLoadingIndicator()
        fun hideLoadingIndicator()
        fun showCrittersAvailableNow(bugFishSummary: BugFishSummaryEntity)
    }

    interface Presenter : FishCaughtContract {
        fun onAttach(newView: View)
        fun onDetach()
    }
}