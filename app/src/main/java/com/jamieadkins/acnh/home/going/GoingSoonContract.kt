package com.jamieadkins.acnh.home.going

import com.jamieadkins.acnh.domain.BugFishSummaryEntity
import com.jamieadkins.acnh.fish.FishCaughtContract

interface GoingSoonContract {

    interface View {
        fun showLoadingIndicator()
        fun hideLoadingIndicator()
        fun showCrittersGoingSoon(goingSoon: BugFishSummaryEntity)
    }

    interface Presenter : FishCaughtContract {
        fun onAttach(newView: View)
        fun onDetach()
    }
}