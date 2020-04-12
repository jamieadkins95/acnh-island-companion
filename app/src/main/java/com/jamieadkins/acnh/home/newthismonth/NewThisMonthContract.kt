package com.jamieadkins.acnh.home.newthismonth

import com.jamieadkins.acnh.domain.BugFishSummaryEntity

interface NewThisMonthContract {

    interface View {
        fun showLoadingIndicator()
        fun hideLoadingIndicator()
        fun showCrittersNewThisMonth(newThisMonth: BugFishSummaryEntity)
    }

    interface Presenter {
        fun onAttach(newView: View)
        fun onDetach()
    }
}