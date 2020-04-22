package com.jamieadkins.acnh.bugs

import com.jamieadkins.acnh.domain.bugs.BugEntity

interface BugContract {

    interface View {
        fun showLoadingIndicator()
        fun hideLoadingIndicator()
        fun showBugs(bugs: List<BugEntity>)
    }

    interface Presenter : BugsCaughtContract {
        fun onAttach(newView: View)
        fun onDetach()
    }
}