package com.jamieadkins.acnh.home.newthismonth

import com.jamieadkins.acnh.domain.BugFishSummaryEntity
import com.jamieadkins.acnh.fish.FishCaughtContract

interface NewThisMonthContract {

    interface View {
        fun showLoadingIndicator()
        fun hideLoadingIndicator()
        fun showCrittersNewThisMonth(newThisMonth: BugFishSummaryEntity)
    }

    interface Presenter : FishCaughtContract {
        fun onAttach(newView: View)
        fun onDetach()
    }
}