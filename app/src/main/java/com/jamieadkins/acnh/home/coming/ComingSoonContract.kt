package com.jamieadkins.acnh.home.coming

import com.jamieadkins.acnh.bugs.BugsCaughtContract
import com.jamieadkins.acnh.domain.BugFishSummaryEntity
import com.jamieadkins.acnh.fish.FishCaughtContract

interface ComingSoonContract {

    interface View {
        fun showLoadingIndicator()
        fun hideLoadingIndicator()
        fun showCrittersComingSoon(comingSoon: BugFishSummaryEntity)
    }

    interface Presenter : FishCaughtContract, BugsCaughtContract {
        fun onAttach(newView: View)
        fun onDetach()
    }
}