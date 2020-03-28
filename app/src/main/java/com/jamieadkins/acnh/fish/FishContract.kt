package com.jamieadkins.acnh.fish

import com.jamieadkins.acnh.domain.FishEntity

interface FishContract {

    interface View {
        fun showLoadingIndicator()
        fun hideLoadingIndicator()
        fun showFish(fish: List<FishEntity>)
    }

    interface Presenter {
        fun onAttach(newView: View)
        fun onDetach()
    }
}