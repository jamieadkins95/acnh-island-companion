package com.jamieadkins.acnh.home

import com.jamieadkins.acnh.domain.AvailableNowEntity
import com.jamieadkins.acnh.domain.bugs.BugEntity
import com.jamieadkins.acnh.domain.fish.FishEntity

interface HomeContract {

    interface View {
        fun showLoadingIndicator()
        fun hideLoadingIndicator()
        fun showCrittersAvailableNow(availableNow: AvailableNowEntity)
        fun showCrittersGoingSoon(goingSoon: List<Any>)
    }

    interface Presenter {
        fun onAttach(newView: View)
        fun onDetach()
    }
}